package van.edu.duanquanlybanhang;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter
        extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> list;

    public ProductAdapter(ArrayList<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product,
                        parent,
                        false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ProductViewHolder holder,
            int position) {

        Product product = list.get(position);

        holder.txtName.setText(product.getName());

        holder.txtPrice.setText(
                "Giá: " + product.getPrice() + "đ");

        holder.txtQuantity.setText(
                "Số lượng: " + product.getQuantity());

        // HIỂN THỊ ẢNH
        int imageResource = holder.itemView.getContext()
                .getResources()
                .getIdentifier(
                        product.getImage(),
                        "drawable",
                        holder.itemView.getContext()
                                .getPackageName());

        holder.imgProduct.setImageResource(imageResource);

        // CLICK SỬA
        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            holder.itemView.getContext(),
                            UpdateProductActivity.class);

            intent.putExtra("id", product.getId());
            intent.putExtra("name", product.getName());

            intent.putExtra("price",
                    String.valueOf(product.getPrice()));

            intent.putExtra("quantity",
                    String.valueOf(product.getQuantity()));

            holder.itemView.getContext()
                    .startActivity(intent);

        });

        // NHẤN GIỮ XÓA
        holder.itemView.setOnLongClickListener(v -> {

            new android.app.AlertDialog.Builder(
                    holder.itemView.getContext())

                    .setTitle("Xóa sản phẩm")
                    .setMessage("Bạn có muốn xóa sản phẩm này?")

                    .setPositiveButton("Xóa",
                            (dialog, which) -> {

                                com.google.firebase.database.FirebaseDatabase
                                        .getInstance()
                                        .getReference("Products")
                                        .child(product.getId())
                                        .removeValue();

                            })

                    .setNegativeButton("Hủy", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice, txtQuantity;
        ImageView imgProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}