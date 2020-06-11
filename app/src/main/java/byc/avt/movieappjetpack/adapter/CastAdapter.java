package byc.avt.movieappjetpack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import byc.avt.movieappjetpack.R;
import byc.avt.movieappjetpack.databinding.ItemCastBinding;
import byc.avt.movieappjetpack.model.Cast;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private Context context;
    private List<Cast> cast;

    public CastAdapter(Context context) {
        this.context = context;
    }

    public void setCasts(List<Cast> casts) {
        this.cast = casts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCastBinding view = DataBindingUtil.inflate(inflater, R.layout.item_cast, viewGroup, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder castViewHolder, int i) {
        castViewHolder.itemView.setCast(cast.get(i));
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        public ItemCastBinding itemView;

        CastViewHolder(@NonNull ItemCastBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
