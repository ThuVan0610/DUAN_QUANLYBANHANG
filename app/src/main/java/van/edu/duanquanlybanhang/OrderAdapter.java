package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter
        extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    ArrayList<OrderItem> list;

    // LẮNG NGHE THAY ĐỔI SỐ LƯỢNG
    public interface OnQuantityChangeListener{
        void onChanged();
    }

    OnQuantityChangeListener listener;

    // CONSTRUCTOR
    public OrderAdapter(ArrayList<OrderItem> list,
                        OnQuantityChangeListener listener) {

        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order,
                        parent,
                        false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull OrderViewHolder holder,
            int position) {

        OrderItem item = list.get(position);

        // HIỂN THỊ TÊN
        holder.txtName.setText(item.getName());

        // HIỂN THỊ SỐ LƯỢNG
        holder.txtQuantity.setText(
                String.valueOf(item.getQuantity()));

        // NÚT +
        holder.btnPlus.setOnClickListener(v -> {

            item.setQuantity(
                    item.getQuantity() + 1);

            notifyDataSetChanged();

            listener.onChanged();
        });

        // NÚT -
        holder.btnMinus.setOnClickListener(v -> {

            if(item.getQuantity() > 1){

                item.setQuantity(
                        item.getQuantity() - 1);

                notifyDataSetChanged();

                listener.onChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // VIEW HOLDER
    public static class OrderViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtName, txtQuantity;

        Button btnPlus, btnMinus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName =
                    itemView.findViewById(R.id.txtName);

            txtQuantity =
                    itemView.findViewById(R.id.txtQuantity);

            btnPlus =
                    itemView.findViewById(R.id.btnPlus);

            btnMinus =
                    itemView.findViewById(R.id.btnMinus);
        }
    }
}