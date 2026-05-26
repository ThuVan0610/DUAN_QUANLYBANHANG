package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter
        extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    ArrayList<Product> list;

    OnAddClickListener listener;

    public interface OnAddClickListener{

        void onAdd(Product product);
    }

    public ProductAdapter(ArrayList<Product> list,
                          OnAddClickListener listener){

        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_product,
                                parent,
                                false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ProductViewHolder holder,
            int position) {

        Product product = list.get(position);

        holder.txtName.setText(
                product.getName());

        holder.txtPrice.setText(
                product.getPrice() + "đ");

        int imageRes =
                holder.itemView.getContext()
                        .getResources()
                        .getIdentifier(
                                product.getImage(),
                                "drawable",
                                holder.itemView
                                        .getContext()
                                        .getPackageName());

        holder.imgProduct.setImageResource(imageRes);

        holder.btnAdd.setOnClickListener(v -> {

            listener.onAdd(product);
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ProductViewHolder
            extends RecyclerView.ViewHolder{

        ImageView imgProduct;

        TextView txtName, txtPrice;

        Button btnAdd;

        public ProductViewHolder(
                @NonNull View itemView) {

            super(itemView);

            imgProduct =
                    itemView.findViewById(R.id.imgProduct);

            txtName =
                    itemView.findViewById(R.id.txtName);

            txtPrice =
                    itemView.findViewById(R.id.txtPrice);

            btnAdd =
                    itemView.findViewById(R.id.btnAdd);
        }
    }
}