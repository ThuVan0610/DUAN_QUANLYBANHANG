package van.edu.duanquanlybanhang;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableAdapter
        extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    ArrayList<TableCafe> list;

    public TableAdapter(ArrayList<TableCafe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table,
                        parent,
                        false);

        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TableViewHolder holder,
            int position) {

        TableCafe table = list.get(position);

        holder.txtTableName.setText(table.getName());

        holder.txtStatus.setText(table.getStatus());

        if(table.getStatus().equals("Trống")){

            holder.cardView.setCardBackgroundColor(
                    Color.parseColor("#BBF7D0"));

        }else{

            holder.cardView.setCardBackgroundColor(
                    Color.parseColor("#FECACA"));
        }
        holder.itemView.setOnClickListener(v -> {

            android.content.Intent intent =
                    new android.content.Intent(
                            holder.itemView.getContext(),
                            OrderActivity.class);

            intent.putExtra(
                    "table",
                    table.getName());

            holder.itemView.getContext()
                    .startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TableViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtTableName, txtStatus;
        CardView cardView;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTableName =
                    itemView.findViewById(R.id.txtTableName);

            txtStatus =
                    itemView.findViewById(R.id.txtStatus);

            cardView = (CardView) itemView;
        }
    }
}