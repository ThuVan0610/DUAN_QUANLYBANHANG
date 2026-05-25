package van.edu.duanquanlybanhang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {

    RecyclerView recyclerTable;

    ArrayList<TableCafe> list;

    TableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        recyclerTable =
                findViewById(R.id.recyclerTable);

        recyclerTable.setLayoutManager(
                new GridLayoutManager(this, 2));

        list = new ArrayList<>();

        list.add(new TableCafe(
                "1",
                "Bàn 1",
                "Trống"));

        list.add(new TableCafe(
                "2",
                "Bàn 2",
                "Có khách"));

        list.add(new TableCafe(
                "3",
                "Bàn 3",
                "Trống"));

        list.add(new TableCafe(
                "4",
                "Bàn 4",
                "Có khách"));

        adapter = new TableAdapter(list);

        recyclerTable.setAdapter(adapter);
    }
}