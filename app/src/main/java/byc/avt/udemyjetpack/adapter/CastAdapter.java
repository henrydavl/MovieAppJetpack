package byc.avt.udemyjetpack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import byc.avt.udemyjetpack.BuildConfig;
import byc.avt.udemyjetpack.R;
import byc.avt.udemyjetpack.model.Cast;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, viewGroup, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder castViewHolder, int i) {
        Cast c = cast.get(i);
        Glide.with(context).load(BuildConfig.BASE_IMAGE_URL + c.getImg_url()).into(castViewHolder.iv_cast);
        castViewHolder.tv_name.setText(c.getName());
        castViewHolder.tv_role.setText(c.getRole());
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_cast;
        TextView tv_name, tv_role;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cast = itemView.findViewById(R.id.cast_img);
            tv_name = itemView.findViewById(R.id.cast_name);
            tv_role = itemView.findViewById(R.id.cast_role);
        }
    }
}
