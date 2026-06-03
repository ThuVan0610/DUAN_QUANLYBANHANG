package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.Locale;
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
    Button btnPickDate;

    String selectedDate = "";
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
        btnPickDate = findViewById(R.id.btnPickDate);
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        selectedDate = sdf.format(new Date());
        btnPickDate.setOnClickListener(v -> {

            Calendar c = Calendar.getInstance();

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            this,
                            (view, year, month, dayOfMonth) -> {

                                selectedDate =
                                        String.format(
                                                Locale.getDefault(),
                                                "%02d/%02d/%d",
                                                dayOfMonth,
                                                month + 1,
                                                year
                                        );

                                filterByDate();
                            },
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)
                    );

            dialog.show();
        });
        loadOrders();
    }
    private void filterByDate() {

        int revenueDay = 0;
        int orderCountDay = 0;

        HashMap<String,Integer> productDay =
                new HashMap<>();

        for (OrderModel o : orderList) {

            if (!selectedDate.equals(o.getDateOnly()))
                continue;

            orderCountDay++;

            try {

                revenueDay += Integer.parseInt(
                        o.getTotal()
                                .replace("đ","")
                                .replace(".","")
                                .trim()
                );

            } catch (Exception ignored){}

            if (o.getItems() != null) {

                for (CartItem item : o.getItems()) {

                    productDay.put(
                            item.getName(),
                            productDay.getOrDefault(
                                    item.getName(),
                                    0
                            ) + item.getQuantity()
                    );
                }
            }
        }

        txtRevenue.setText(
                "Doanh thu: " + revenueDay + "đ"
        );

        txtOrderCount.setText(
                "Số đơn: " + orderCountDay
        );

        txtItemCount.setText(
                "Số món: " + productDay.size()
        );
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

                    // Tổng doanh thu
                    try {

                        if (o.getTotal() != null) {

                            revenue += Integer.parseInt(
                                    o.getTotal()
                                            .replace("đ", "")
                                            .replace(".", "")
                                            .trim()
                            );
                        }

                    } catch (Exception ignored) {
                    }

                    // Thống kê món bán
                    if (o.getItems() != null) {

                        for (CartItem item : o.getItems()) {

                            if (item.getName() == null) continue;

                            productCount.put(
                                    item.getName(),
                                    productCount.getOrDefault(
                                            item.getName(),
                                            0
                                    ) + item.getQuantity()
                            );
                        }
                    }
                }

                // Đơn mới nhất lên đầu
                Collections.reverse(orderList);

                filterByDate();

                updateTopProducts();
                updateChart();

                orderAdapter.updateData(
                        new ArrayList<>(orderList)
                );
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void updateTopProducts() {

        ArrayList<TopItem> list = new ArrayList<>();

        for (String key : productCount.keySet()) {

            list.add(
                    new TopItem(
                            key,
                            productCount.get(key)
                    )
            );
        }

        // Sắp xếp giảm dần
        list.sort((a, b) ->
                Integer.compare(
                        b.getCount(),
                        a.getCount()
                ));

        topAdapter.update(list);
    }

    private void updateChart() {

        HashMap<String, Integer> revenueByDate =
                new HashMap<>();

        for (OrderModel order : orderList) {

            try {

                String date = order.getDateOnly();

                if (date == null) continue;

                int total = Integer.parseInt(
                        order.getTotal()
                                .replace("đ", "")
                                .replace(".", "")
                                .trim()
                );

                revenueByDate.put(
                        date,
                        revenueByDate.getOrDefault(date, 0)
                                + total
                );

            } catch (Exception ignored) {
            }
        }

        ArrayList<String> dates =
                new ArrayList<>(revenueByDate.keySet());

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Collections.sort(dates, (a, b) -> {
            try {
                return sdf.parse(a).compareTo(sdf.parse(b));
            } catch (Exception e) {
                return 0;
            }
        });



        ArrayList<BarEntry> entries =
                new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {

            entries.add(
                    new BarEntry(
                            i,
                            revenueByDate.get(
                                    dates.get(i)
                            )
                    )
            );
        }

        BarDataSet dataSet =
                new BarDataSet(
                        entries,
                        "Doanh thu theo ngày"
                );

        dataSet.setValueTextSize(10f);

        BarData data = new BarData(dataSet);

        chart.setData(data);

// ===== Hiển thị ngày dưới cột =====
        XAxis xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setGranularity(1f);

        xAxis.setLabelCount(dates.size());

        xAxis.setValueFormatter(
                new IndexAxisValueFormatter(dates)
        );

// ===== Tắt mô tả =====
        chart.getDescription().setEnabled(false);

        chart.animateY(1000);

        chart.invalidate();
    }
}