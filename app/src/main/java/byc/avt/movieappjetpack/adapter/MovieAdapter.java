package byc.avt.movieappjetpack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import byc.avt.movieappjetpack.R;
import byc.avt.movieappjetpack.databinding.ItemBinding;
import byc.avt.movieappjetpack.model.Movie;
import byc.avt.movieappjetpack.util.ItemClickListener;
import byc.avt.movieappjetpack.view.fragment.MovieFragmentDirections;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements ItemClickListener {

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
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBinding view = DataBindingUtil.inflate(inflater, R.layout.item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.itemView.setMovie(moviesData.get(i));
        movieViewHolder.itemView.setListener(this);
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    @Override
    public void onMovieClicked(View view, Movie movie) {
        MovieFragmentDirections.ActionDetail action = MovieFragmentDirections.actionDetail(movie);
        Navigation.findNavController(view).navigate(action);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        public ItemBinding itemView;

        MovieViewHolder(@NonNull ItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
