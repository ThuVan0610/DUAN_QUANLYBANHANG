package van.edu.duanquanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    TextView txtTable;
    RecyclerView recyclerProduct;
    Button btnClear, btnViewCart;

    public static String currentTable = "";
    public static ArrayList<CartItem> cartList = new ArrayList<>();

    ArrayList<Product> productList;
    ProductAdapter adapter;
    DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // ÁNH XẠ
        txtTable = findViewById(R.id.txtTable);
        recyclerProduct = findViewById(R.id.recyclerProduct);
        btnClear = findViewById(R.id.btnClear);
        btnViewCart = findViewById(R.id.btnViewCart);

        // =========================
        // LẤY BÀN ĐÚNG CÁCH
        // =========================
        currentTable = getIntent().getStringExtra("table");

        txtTable.setText(currentTable);

        // RECYCLER
        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        // FIREBASE PRODUCT
        productRef = FirebaseDatabase.getInstance()
                .getReference("Products");

        adapter = new ProductAdapter(productList, product -> {
            addToCart(product);
        });

        recyclerProduct.setAdapter(adapter);

        loadProducts();

        // XÓA GIỎ HÀNG
        btnClear.setOnClickListener(v -> {
            cartList.clear();
            Toast.makeText(this, "Đã xóa giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // XEM GIỎ HÀNG
        btnViewCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    // THÊM GIỎ HÀNG
    private void addToCart(Product product) {

        boolean found = false;

        for (CartItem item : cartList) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            cartList.add(new CartItem(
                    product.getName(),
                    product.getPrice(),
                    1
            ));
        }

        Toast.makeText(this,
                "Đã thêm " + product.getName(),
                Toast.LENGTH_SHORT).show();
    }

    // LOAD PRODUCT FIREBASE
    private void loadProducts() {

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                productList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Product product = data.getValue(Product.class);

                    if (product != null) {
                        productList.add(product);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}