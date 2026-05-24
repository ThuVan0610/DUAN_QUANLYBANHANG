package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    EditText edtName, edtPrice, edtQuantity;
    Button btnSave;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnSave = findViewById(R.id.btnSave);

        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("Products");

        btnSave.setOnClickListener(v -> {

            String id = databaseReference.push().getKey();

            String name = edtName.getText().toString();

            double price =
                    Double.parseDouble(
                            edtPrice.getText().toString());

            int quantity =
                    Integer.parseInt(
                            edtQuantity.getText().toString());

            Product product =
                    new Product(id, name, price, quantity, "cafe");

            databaseReference.child(id)
                    .setValue(product)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(this,
                                "Thêm sản phẩm thành công",
                                Toast.LENGTH_SHORT).show();

                        finish();

                    });

        });

    }
}