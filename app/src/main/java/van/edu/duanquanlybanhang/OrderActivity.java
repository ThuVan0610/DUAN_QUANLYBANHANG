package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    TextView txtTable, txtTotal;

    Button btnCafe, btnTraDao, btnPayment;

    RecyclerView recyclerOrder;

    ArrayList<OrderItem> list;

    OrderAdapter adapter;

    double total = 0;

    DatabaseReference billRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // ÁNH XẠ VIEW
        txtTable = findViewById(R.id.txtTable);
        txtTotal = findViewById(R.id.txtTotal);

        btnCafe = findViewById(R.id.btnCafe);
        btnTraDao = findViewById(R.id.btnTraDao);
        btnPayment = findViewById(R.id.btnPayment);

        recyclerOrder = findViewById(R.id.recyclerOrder);

        // FIREBASE
        billRef = FirebaseDatabase
                .getInstance()
                .getReference("Bills");

        // NHẬN TÊN BÀN
        String tableName =
                getIntent().getStringExtra("table");

        txtTable.setText(tableName);

        // RECYCLERVIEW
        recyclerOrder.setLayoutManager(
                new LinearLayoutManager(this));

        list = new ArrayList<>();

        adapter = new OrderAdapter(list, () -> {
            updateTotal();
        });

        recyclerOrder.setAdapter(adapter);

        // THÊM CAFE SỮA
        btnCafe.setOnClickListener(v -> {

            addItem(
                    "Cafe sữa",
                    25000
            );
        });

        // THÊM TRÀ ĐÀO
        btnTraDao.setOnClickListener(v -> {

            addItem(
                    "Trà đào",
                    30000
            );
        });

        // THANH TOÁN
        btnPayment.setOnClickListener(v -> {

            if(list.isEmpty()){

                Toast.makeText(
                        this,
                        "Chưa có món để thanh toán",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            android.app.AlertDialog.Builder builder =
                    new android.app.AlertDialog.Builder(this);

            builder.setTitle("Thanh toán");

            builder.setMessage(
                    "Xác nhận thanh toán hóa đơn?");

            builder.setPositiveButton(
                    "Thanh toán",
                    (dialog, which) -> {

                        // THỜI GIAN
                        String time =
                                new SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm",
                                        Locale.getDefault())
                                        .format(new Date());

                        // TẠO BILL
                        Bill bill =
                                new Bill(
                                        txtTable.getText()
                                                .toString(),
                                        total,
                                        time,
                                        list);

                        // TẠO ID
                        String id =
                                billRef.push().getKey();

                        // LƯU FIREBASE
                        if(id != null){

                            billRef.child(id)
                                    .setValue(bill);
                        }

                        // RESET BÀN
                        setResult(RESULT_OK);

                        // THÔNG BÁO
                        Toast.makeText(
                                this,
                                "Thanh toán thành công",
                                Toast.LENGTH_SHORT
                        ).show();

                        // RESET DANH SÁCH
                        list.clear();

                        adapter.notifyDataSetChanged();

                        // RESET TỔNG TIỀN
                        total = 0;

                        txtTotal.setText("Tổng: 0đ");

                        // ĐÓNG ACTIVITY
                        finish();

                    });

            builder.setNegativeButton(
                    "Hủy",
                    null);

            builder.show();

        });

    }

    // HÀM THÊM MÓN
    private void addItem(String name,
                         double price){

        boolean found = false;

        for(OrderItem item : list){

            if(item.getName().equals(name)){

                item.setQuantity(
                        item.getQuantity() + 1);

                found = true;

                break;
            }
        }

        if(!found){

            list.add(
                    new OrderItem(
                            name,
                            price,
                            1
                    )
            );
        }

        adapter.notifyDataSetChanged();

        updateTotal();
    }

    // HÀM TÍNH TỔNG TIỀN
    private void updateTotal(){

        total = 0;

        for(OrderItem item : list){

            total += item.getPrice()
                    * item.getQuantity();
        }

        txtTotal.setText(
                "Tổng: " + total + "đ");
    }
}