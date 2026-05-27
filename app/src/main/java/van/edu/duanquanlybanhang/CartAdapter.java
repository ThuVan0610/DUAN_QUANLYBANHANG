package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    ArrayList<CartItem> list;

    // CALLBACK
    public interface OnCartChangeListener {
        void onCartChanged();
    }

    private OnCartChangeListener listener;

    // CONSTRUCTOR 1
    public CartAdapter(ArrayList<CartItem> list) {
        this.list = list;
    }

    // CONSTRUCTOR 2 (FIX ĐÚNG Ở ĐÂY)
    public CartAdapter(ArrayList<CartItem> list,
                       OnCartChangeListener listener) {
        this.list = list;
        this.listener = listener;
    }

    // SET LISTENER (optional)
    public void setOnCartChangeListener(OnCartChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        CartItem item = list.get(position);

        holder.txtName.setText(item.getName());
        holder.txtQuantity.setText("x" + item.getQuantity());

        // ➖ GIẢM
        holder.btnMinus.setOnClickListener(v -> {

            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
            }

            if (listener != null) listener.onCartChanged();
        });

        // ➕ TĂNG
        holder.btnPlus.setOnClickListener(v -> {

            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);

            if (listener != null) listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtQuantity, btnMinus, btnPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}