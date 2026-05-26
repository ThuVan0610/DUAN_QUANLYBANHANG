package van.edu.duanquanlybanhang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableAdapter
        extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    Context context;
    ArrayList<Table> list;

    public TableAdapter(ArrayList<Table> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_table, parent, false);

        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TableViewHolder holder,
            int position) {

        Table table = list.get(position);

        holder.txtTableName.setText(table.getName());

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra("table", table.getName());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {

        TextView txtTableName;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTableName = itemView.findViewById(R.id.txtTableName);
        }
    }
}