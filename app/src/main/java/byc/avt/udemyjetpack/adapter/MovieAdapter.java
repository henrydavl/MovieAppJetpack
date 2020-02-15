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

import java.util.ArrayList;
import java.util.List;

import byc.avt.udemyjetpack.BuildConfig;
import byc.avt.udemyjetpack.R;
import byc.avt.udemyjetpack.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> moviesData;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.moviesData = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie m = moviesData.get(i);
        Glide.with(context)
                .load(BuildConfig.BASE_IMAGE_URL + m.getCover())
                .into(movieViewHolder.movie_cover);
        movieViewHolder.movie_title.setText(m.getTitle());
        movieViewHolder.movie_popular.setText(m.getPopularity());
        movieViewHolder.movie_date.setText(m.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_cover;
        TextView movie_title, movie_popular, movie_date;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_cover = itemView.findViewById(R.id.item_cover);
            movie_title = itemView.findViewById(R.id.item_title);
            movie_popular = itemView.findViewById(R.id.item_popular);
            movie_date = itemView.findViewById(R.id.item_date);
        }
    }
}
