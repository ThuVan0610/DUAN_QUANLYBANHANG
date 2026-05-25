package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillAdapter
        extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    ArrayList<Bill> list;

    public BillAdapter(ArrayList<Bill> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill,
                        parent,
                        false);

        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull BillViewHolder holder,
            int position) {

        Bill bill = list.get(position);

        holder.txtBillTable.setText(
                bill.getTable());

        holder.txtBillTotal.setText(
                "Tổng tiền: " +
                        bill.getTotal() + "đ");

        holder.txtBillTime.setText(
                bill.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BillViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtBillTable,
                txtBillTotal,
                txtBillTime;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBillTable =
                    itemView.findViewById(R.id.txtBillTable);

            txtBillTotal =
                    itemView.findViewById(R.id.txtBillTotal);

            txtBillTime =
                    itemView.findViewById(R.id.txtBillTime);
        }
    }
}