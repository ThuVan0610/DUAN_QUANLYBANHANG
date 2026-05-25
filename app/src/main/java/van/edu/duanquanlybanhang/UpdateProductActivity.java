package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class UpdateProductActivity extends AppCompatActivity {

    EditText edtName, edtPrice, edtQuantity;
    Button btnUpdate;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnUpdate = findViewById(R.id.btnUpdate);

        id = getIntent().getStringExtra("id");

        edtName.setText(getIntent().getStringExtra("name"));

        edtPrice.setText(
                getIntent().getStringExtra("price"));

        edtQuantity.setText(
                getIntent().getStringExtra("quantity"));

        btnUpdate.setOnClickListener(v -> {

            String name =
                    edtName.getText().toString();

            double price =
                    Double.parseDouble(
                            edtPrice.getText().toString());

            int quantity =
                    Integer.parseInt(
                            edtQuantity.getText().toString());

            Product product =
                    new Product(id, name, price, quantity, "cafesua");

            FirebaseDatabase.getInstance()
                    .getReference("Products")
                    .child(id)
                    .setValue(product)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(this,
                                "Cập nhật thành công",
                                Toast.LENGTH_SHORT).show();

                        finish();

                    });

        });

    }
}