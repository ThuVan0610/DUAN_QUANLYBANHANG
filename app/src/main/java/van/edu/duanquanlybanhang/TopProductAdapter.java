package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopProductAdapter
        extends RecyclerView.Adapter<TopProductAdapter.VH> {

    ArrayList<TopItem> list;

    public TopProductAdapter(ArrayList<TopItem> list) {
        this.list = list;
    }

    public void update(ArrayList<TopItem> newList) {
        this.list = newList;
        notifyDataSetChanged();
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

        TopItem item = list.get(i);

        h.t1.setText(item.getName());
        h.t2.setText("Đã bán: " + item.getCount());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView t1, t2;

        public VH(@NonNull View v) {
            super(v);
            t1 = v.findViewById(android.R.id.text1);
            t2 = v.findViewById(android.R.id.text2);
        }
    }
}