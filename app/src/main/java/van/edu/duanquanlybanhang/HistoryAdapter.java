package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter
        extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    ArrayList<OrderModel> list;
    ArrayList<OrderModel> backupList;

    public HistoryAdapter(ArrayList<OrderModel> list) {
        this.list = list;
        this.backupList = new ArrayList<>(list);
    }

    public void updateData(ArrayList<OrderModel> newList) {
        this.list = newList;
        this.backupList = new ArrayList<>(newList); // ⭐ quan trọng
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        OrderModel order = list.get(position);

        holder.txtTable.setText(order.getTable());
        holder.txtTotal.setText(order.getTotal());
        holder.txtDate.setText(order.getDate());

        // ⭐ CHÈN HIỂN THỊ MÓN
        StringBuilder itemsText = new StringBuilder();

        if (order.getItems() != null) {
            for (CartItem item : order.getItems()) {
                itemsText.append(item.getName())
                        .append(" x")
                        .append(item.getQuantity())
                        .append("\n");
            }
            holder.txtItems.setText(itemsText.toString());
        } else {
            itemsText.append("Không có dữ liệu món");
        }

        // click để xem chi tiết món
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(
                    v.getContext(),
                    itemsText.toString(),
                    Toast.LENGTH_LONG
            ).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // SEARCH FILTER (chỉ tìm theo bàn)
    public void filter(String text) {

        list.clear();

        if (text.isEmpty()) {
            list.addAll(backupList);
        } else {

            text = text.toLowerCase();

            for (OrderModel order : backupList) {

                if (order.getTable().toLowerCase().contains(text)) {
                    list.add(order);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTable, txtTotal, txtDate, txtItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTable = itemView.findViewById(R.id.txtTable);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtItems = itemView.findViewById(R.id.txtItems);
        }
    }
}