package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ManagerActivity extends AppCompatActivity {

    TextView txtRevenue, txtOrderCount;
    BarChart chart;

    RecyclerView recyclerTop;
    TopProductAdapter adapter;

    ArrayList<OrderModel> orderList = new ArrayList<>();
    HashMap<String, Integer> productCount = new HashMap<>();

    int revenue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        txtRevenue = findViewById(R.id.txtRevenue);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        chart = findViewById(R.id.chart);

        recyclerTop = findViewById(R.id.recyclerTop);
        recyclerTop.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TopProductAdapter(new ArrayList<>());
        recyclerTop.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {

        DatabaseReference db =
                FirebaseDatabase.getInstance().getReference("orders");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                orderList.clear();
                productCount.clear();
                revenue = 0;

                for (DataSnapshot data : snapshot.getChildren()) {

                    OrderModel o = data.getValue(OrderModel.class);

                    if (o == null) continue;

                    orderList.add(o);

                    // =========================
                    // 💰 REVENUE SAFE PARSE
                    // =========================
                    String clean = o.getTotal()
                            .replace("đ", "")
                            .replace(".", "")
                            .trim();

                    try {
                        revenue += Integer.parseInt(clean);
                    } catch (Exception ignored) {}

                    // =========================
                    // 🏆 TOP (THEO MÓN)
                    // =========================
                    if (o.getItems() != null) {
                        for (CartItem item : o.getItems()) {

                            String name = item.getName();
                            int qty = item.getQuantity();

                            productCount.put(name,
                                    productCount.getOrDefault(name, 0) + qty);
                        }
                    }
                }

                txtRevenue.setText("Doanh thu: " + revenue + "đ");
                txtOrderCount.setText("Số đơn: " + orderList.size());

                updateTopProducts();
                updateChart();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    // =========================
    // 🏆 TOP MÓN BÁN CHẠY
    // =========================
    private void updateTopProducts() {

        ArrayList<TopItem> list = new ArrayList<>();

        for (String key : productCount.keySet()) {
            list.add(new TopItem(key, productCount.get(key)));
        }

        // sort giảm dần
        Collections.sort(list, (a, b) ->
                Integer.compare(b.getCount(), a.getCount()));

        adapter.update(list);
    }

    // =========================
    // 📊 CHART DOANH THU
    // =========================
    private void updateChart() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        int i = 0;

        for (OrderModel o : orderList) {

            String clean = o.getTotal()
                    .replace("đ", "")
                    .replace(".", "")
                    .trim();

            try {
                entries.add(new BarEntry(i++, Integer.parseInt(clean)));
            } catch (Exception ignored) {}
        }

        BarDataSet set = new BarDataSet(entries, "Doanh thu");
        BarData data = new BarData(set);

        chart.setData(data);
        chart.invalidate();
    }
}