package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.VH> {

    ArrayList<OrderModel> list;

    public ManagerAdapter(ArrayList<OrderModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);

        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {

        OrderModel o = list.get(i);

        h.t1.setText(o.getTable());
        h.t2.setText(o.getTotal() + " - " + o.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView t1, t2;

        public VH(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(android.R.id.text1);
            t2 = itemView.findViewById(android.R.id.text2);
        }
    }
}