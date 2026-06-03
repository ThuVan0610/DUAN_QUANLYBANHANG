package van.edu.duanquanlybanhang;

import android.content.Intent;
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

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerCart;
    TextView txtTotal;
    Button btnPayment;

    CartAdapter adapter;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = findViewById(R.id.recyclerCart);
        txtTotal = findViewById(R.id.txtTotal);
        btnPayment = findViewById(R.id.btnPayment);

        recyclerCart.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CartAdapter(OrderActivity.cartList, this::updateTotal);
        recyclerCart.setAdapter(adapter);

        updateTotal();

        btnPayment.setOnClickListener(v -> {

            if (OrderActivity.cartList == null || OrderActivity.cartList.isEmpty()) {
                Toast.makeText(this, "Chưa có món", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("orders");

            String orderId = db.push().getKey();

            String time = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
            ).format(new Date());

            String dateOnly = new SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
            ).format(new Date());

            int finalTotal = (int) total;

            OrderModel order = new OrderModel(
                    OrderActivity.currentTable,
                    String.valueOf(finalTotal),
                    time,
                    dateOnly,
                    new ArrayList<>(OrderActivity.cartList)
            );

            db.child(orderId).setValue(order)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Đã lưu đơn", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi Firebase", Toast.LENGTH_SHORT).show()
                    );

            OrderActivity.cartList.clear();
            adapter.notifyDataSetChanged();
            updateTotal();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void updateTotal() {

        total = 0;

        if (OrderActivity.cartList != null) {
            for (CartItem item : OrderActivity.cartList) {
                total += item.getPrice() * item.getQuantity();
            }
        }

        txtTotal.setText("Tổng: " + (int) total + ".0đ");
    }
}