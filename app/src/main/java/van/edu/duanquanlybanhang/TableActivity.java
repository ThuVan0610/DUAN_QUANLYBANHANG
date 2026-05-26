package van.edu.duanquanlybanhang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableActivity
        extends AppCompatActivity {

    RecyclerView recyclerTable;

    ArrayList<Table> list;

    TableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        recyclerTable =
                findViewById(R.id.recyclerTable);

        recyclerTable.setLayoutManager(
                new GridLayoutManager(this,2));

        list = new ArrayList<>();

        list.add(new Table("MANG VỀ"));
        list.add(new Table("BÀN 1"));
        list.add(new Table("BÀN 2"));
        list.add(new Table("BÀN 3"));
        list.add(new Table("BÀN 4"));
        list.add(new Table("BÀN 5"));
        list.add(new Table("BÀN 6"));
        list.add(new Table("BÀN 7"));
        list.add(new Table("BÀN 8"));
        list.add(new Table("BÀN 9"));

        adapter = new TableAdapter(list);

        recyclerTable.setAdapter(adapter);
    }
}