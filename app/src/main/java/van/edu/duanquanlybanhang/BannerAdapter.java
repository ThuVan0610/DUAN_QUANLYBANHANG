package van.edu.duanquanlybanhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.VH> {

    int[] images;

    public BannerAdapter(int[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        h.img.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    // ===== VIEW HOLDER =====
    static class VH extends RecyclerView.ViewHolder {

        ImageView img;

        VH(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.img);
        }
    }
}