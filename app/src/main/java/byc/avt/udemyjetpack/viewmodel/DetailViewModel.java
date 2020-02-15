package byc.avt.udemyjetpack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import byc.avt.udemyjetpack.model.Cast;
import byc.avt.udemyjetpack.model.Genre;
import byc.avt.udemyjetpack.repository.MovieRepository;

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
