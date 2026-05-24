package van.edu.duanquanlybanhang;

import android.os.Bundle;

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

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerProduct;

    ArrayList<Product> list;

    ProductAdapter adapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerProduct = findViewById(R.id.recyclerProduct);

        recyclerProduct.setLayoutManager(
                new LinearLayoutManager(this));

        list = new ArrayList<>();

        adapter = new ProductAdapter(list);

        recyclerProduct.setAdapter(adapter);

        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("Products");

        databaseReference.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot snapshot) {

                        list.clear();

                        for(DataSnapshot dataSnapshot :
                                snapshot.getChildren()) {

                            Product product =
                                    dataSnapshot.getValue(Product.class);

                            list.add(product);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError error) {

                    }
                });

    }
}