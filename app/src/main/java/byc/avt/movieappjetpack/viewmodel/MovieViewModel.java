package byc.avt.movieappjetpack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import byc.avt.movieappjetpack.model.Movie;
import byc.avt.movieappjetpack.repository.MovieRepository;

public class MovieViewModel extends ViewModel {

    private MovieRepository movieRepository;
    public MutableLiveData<Boolean> movieError = new MutableLiveData<>();

    public MovieViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Movie>> getMovieDiscovery() {
        return movieRepository.getMovieDiscovery();
    }

    public void refresh() {
        getMovieDiscovery();
    }
}
