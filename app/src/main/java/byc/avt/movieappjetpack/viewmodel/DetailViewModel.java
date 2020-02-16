package byc.avt.movieappjetpack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import byc.avt.movieappjetpack.model.Cast;
import byc.avt.movieappjetpack.model.Genre;
import byc.avt.movieappjetpack.repository.MovieRepository;

public class DetailViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public DetailViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Genre>> getGenre(String id){
        return movieRepository.getMovieGenre(id);
    }

    public LiveData<List<Cast>> getCast(String id){
        return movieRepository.getCastMovie(id);
    }
}
