package byc.avt.movieappjetpack.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import byc.avt.movieappjetpack.R;
import byc.avt.movieappjetpack.adapter.MovieAdapter;
import byc.avt.movieappjetpack.model.Movie;
import byc.avt.movieappjetpack.util.ItemClickSupport;
import byc.avt.movieappjetpack.view.MainActivity;
import byc.avt.movieappjetpack.viewmodel.MovieViewModel;

public class MovieFragment extends Fragment {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.listError)
    TextView tvError;

    private MovieViewModel viewModel;
    private MovieAdapter movieAdapter;
    private List<Movie> temp = new ArrayList<>();

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_outline_movie);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(" " + getString(R.string.title_movie));
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getActivity());
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieViewModel.class);
        viewModel.getMovieDiscovery().observe(getActivity(), getMovieData);
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));
        clickSupport(view);
    }

    private void clickSupport(View view) {
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
            MovieFragmentDirections.ActionDetail action = MovieFragmentDirections.actionDetail(temp.get(position));
            Navigation.findNavController(view).navigate(action);
        });
    }

    private Observer<List<Movie>> getMovieData = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            if (movies != null){
                temp.addAll(movies);
                movieAdapter.setMovies(movies);
                movieAdapter.notifyDataSetChanged();
                rvMovie.setAdapter(movieAdapter);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
