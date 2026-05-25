package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class DashboardActivity extends AppCompatActivity {

    TextView txtRevenue, txtBillCount, txtBestSeller;

    BarChart barChart;

    DatabaseReference billRef;

    double totalRevenue = 0;

    int billCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ÁNH XẠ VIEW
        txtRevenue =
                findViewById(R.id.txtRevenue);

        txtBillCount =
                findViewById(R.id.txtBillCount);

        txtBestSeller =
                findViewById(R.id.txtBestSeller);

        barChart =
                findViewById(R.id.barChart);

        // FIREBASE
        billRef = FirebaseDatabase
                .getInstance()
                .getReference("Bills");

        // LOAD DASHBOARD
        loadDashboard();
    }

    private void loadDashboard(){

        billRef.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot snapshot) {

                        // RESET
                        totalRevenue = 0;

                        billCount = 0;

                        // BIỂU ĐỒ
                        ArrayList<BarEntry> entries =
                                new ArrayList<>();

                        // HASHMAP ĐẾM MÓN
                        HashMap<String, Integer> map =
                                new HashMap<>();

                        // DUYỆT HÓA ĐƠN
                        for(DataSnapshot data :
                                snapshot.getChildren()){

                            Bill bill =
                                    data.getValue(Bill.class);

                            if(bill != null){

                                // DOANH THU
                                totalRevenue += bill.getTotal();

                                // SỐ HÓA ĐƠN
                                billCount++;

                                // BIỂU ĐỒ
                                entries.add(
                                        new BarEntry(
                                                billCount,
                                                (float) bill.getTotal()));

                                // THỐNG KÊ MÓN
                                if(bill.getItems() != null){

                                    for(OrderItem item :
                                            bill.getItems()){

                                        int current =
                                                map.getOrDefault(
                                                        item.getName(),
                                                        0);

                                        map.put(
                                                item.getName(),
                                                current
                                                        + item.getQuantity());
                                    }
                                }
                            }
                        }

                        // HIỂN THỊ DOANH THU
                        txtRevenue.setText(
                                totalRevenue + "đ");

                        // HIỂN THỊ SỐ HÓA ĐƠN
                        txtBillCount.setText(
                                String.valueOf(billCount));

                        // TÌM MÓN BÁN CHẠY
                        String bestName = "";

                        int max = 0;

                        for(String key : map.keySet()){

                            int value = map.get(key);

                            if(value > max){

                                max = value;

                                bestName = key;
                            }
                        }

                        // HIỂN THỊ BEST SELLER
                        txtBestSeller.setText(
                                "Món bán chạy: "
                                        + bestName
                                        + " (" + max + ")");

                        // TẠO DATASET
                        BarDataSet dataSet =
                                new BarDataSet(
                                        entries,
                                        "Doanh thu");

                        // DATA CHART
                        BarData chartData =
                                new BarData(dataSet);

                        // SET CHART
                        barChart.setData(chartData);

                        // REFRESH
                        barChart.invalidate();
                    }

                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError error) {

                    }
                });
    }
}