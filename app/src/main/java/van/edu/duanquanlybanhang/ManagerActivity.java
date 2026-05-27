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
import java.util.HashMap;

public class ManagerActivity extends AppCompatActivity {

    TextView txtRevenue, txtOrderCount, txtItemCount;
    BarChart chart;

    RecyclerView recyclerTop, recyclerManager;

    TopProductAdapter topAdapter;
    HistoryAdapter orderAdapter;

    ArrayList<OrderModel> orderList = new ArrayList<>();
    HashMap<String, Integer> productCount = new HashMap<>();

    int revenue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        txtRevenue = findViewById(R.id.txtRevenue);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        txtItemCount = findViewById(R.id.txtItemCount);
        chart = findViewById(R.id.chart);

        recyclerTop = findViewById(R.id.recyclerTop);
        recyclerManager = findViewById(R.id.recyclerManager);

        recyclerTop.setLayoutManager(new LinearLayoutManager(this));
        recyclerManager.setLayoutManager(new LinearLayoutManager(this));

        topAdapter = new TopProductAdapter(new ArrayList<>());
        orderAdapter = new HistoryAdapter(new ArrayList<>());

        recyclerTop.setAdapter(topAdapter);
        recyclerManager.setAdapter(orderAdapter);

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

                    // ================= SAFE REVENUE =================
                    String total = o.getTotal();
                    if (total != null) {
                        try {
                            revenue += Integer.parseInt(
                                    total.replace("đ", "")
                                            .replace(".", "")
                                            .trim()
                            );
                        } catch (Exception ignored) {}
                    }

                    // ================= TOP ITEM =================
                    if (o.getItems() != null) {
                        for (CartItem item : o.getItems()) {

                            if (item.getName() == null) continue;

                            productCount.put(
                                    item.getName(),
                                    productCount.getOrDefault(item.getName(), 0)
                                            + item.getQuantity()
                            );
                        }
                    }
                }

                txtRevenue.setText("Doanh thu: " + revenue + "đ");
                txtOrderCount.setText("Số đơn: " + orderList.size());
                txtItemCount.setText("Số món: " + productCount.size());

                updateTopProducts();
                updateChart();

                orderAdapter.updateData(new ArrayList<>(orderList));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // tránh crash
            }
        });
    }

    private void updateTopProducts() {

        ArrayList<TopItem> list = new ArrayList<>();

        for (String key : productCount.keySet()) {
            list.add(new TopItem(key, productCount.get(key)));
        }

        topAdapter.update(list);
    }

    private void updateChart() {

        ArrayList<BarEntry> entries = new ArrayList<>();
        int i = 0;

        for (OrderModel o : orderList) {

            String total = o.getTotal();
            if (total == null) continue;

            try {
                int value = Integer.parseInt(
                        total.replace("đ", "")
                                .replace(".", "")
                                .trim()
                );

                entries.add(new BarEntry(i++, value));

            } catch (Exception ignored) {}
        }

        BarDataSet set = new BarDataSet(entries, "Doanh thu");
        BarData data = new BarData(set);

        chart.setData(data);
        chart.invalidate();
    }
}