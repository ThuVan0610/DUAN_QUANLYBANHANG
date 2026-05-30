package van.edu.duanquanlybanhang;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerHistory;

    HistoryAdapter adapter;

    ArrayList<OrderModel> list;
    ArrayList<OrderModel> backupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        searchView = findViewById(R.id.searchView);
        recyclerHistory = findViewById(R.id.recyclerHistory);

        list = new ArrayList<>();
        backupList = new ArrayList<>();

        recyclerHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new HistoryAdapter(list);
        recyclerHistory.setAdapter(adapter);

        loadData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                filterData(text);
                return true;
            }
        });
    }

    // ================= FIREBASE =================
    private void loadData() {
        DatabaseReference db =
                FirebaseDatabase.getInstance().getReference("orders");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                backupList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    OrderModel order = data.getValue(OrderModel.class);
                    if (order != null) {
                        list.add(order);
                        backupList.add(order);
                    }
                }
                // Đảo ngược danh sách để đơn mới nhất lên đầu
                Collections.reverse(list);
                Collections.reverse(backupList);
                adapter.updateData(new ArrayList<>(list));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    // ================= SEARCH =================
    private void filterData(String text) {
        ArrayList<OrderModel> temp = new ArrayList<>();
        text = text.toLowerCase();
        for (OrderModel order : backupList) {
            String table = safe(order.getTable());
            String total = safe(order.getTotal());
            String date = safe(order.getDate());
            if (table.contains(text)
                    || total.contains(text)
                    || date.contains(text)) {
                temp.add(order);
            }
        }
        adapter.updateData(temp);
    }
    // ================= FILTER TABLE =================
    private void filterByTable(String tableName) {

        ArrayList<OrderModel> temp = new ArrayList<>();

        for (OrderModel order : backupList) {
            if (safe(order.getTable()).equalsIgnoreCase(tableName)) {
                temp.add(order);
            }
        }

        adapter.updateData(temp);
    }

    // ================= SAFE STRING =================
    private String safe(String s) {
        return s == null ? "" : s.toLowerCase();
    }
}