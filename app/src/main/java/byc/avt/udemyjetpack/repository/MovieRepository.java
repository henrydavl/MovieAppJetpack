package byc.avt.udemyjetpack.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import byc.avt.udemyjetpack.BuildConfig;
import byc.avt.udemyjetpack.model.Cast;
import byc.avt.udemyjetpack.model.CastResponse;
import byc.avt.udemyjetpack.model.Genre;
import byc.avt.udemyjetpack.model.GenreResponse;
import byc.avt.udemyjetpack.model.Movie;
import byc.avt.udemyjetpack.model.MovieResponse;
import byc.avt.udemyjetpack.util.ApiService;
import byc.avt.udemyjetpack.util.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ApiService apiService;
    private static MovieRepository repository;
    private String apiKey = BuildConfig.API_KEY;

    private MovieRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public static MovieRepository getInstance() {
        if (repository == null) {
            repository = new MovieRepository(RetrofitService.createService(ApiService.class));
        }
        return repository;
    }

    public MutableLiveData<List<Movie>> getMovieDiscovery() {
        MutableLiveData<List<Movie>> listMovies = new MutableLiveData<>();
        apiService.getMovie(apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        listMovies.postValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Movie Discovery Error", t.getMessage());
            }
        });
        return listMovies;
    }

    public MutableLiveData<List<Genre>> getMovieGenre(String id) {
        MutableLiveData<List<Genre>> listGenres = new MutableLiveData<>();
        apiService.getMovieGenre(Integer.valueOf(id), apiKey).enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        listGenres.postValue(response.body().getGenres());
                    }
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Log.e("Movie Genre Error", t.getMessage());
            }
        });
        return listGenres;
    }

    public MutableLiveData<List<Cast>> getCastMovie(String id) {
        MutableLiveData<List<Cast>> listCast = new MutableLiveData<>();
        apiService.getCastMovie(Integer.valueOf(id), apiKey).enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        listCast.postValue(response.body().getCast());
                    }
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Log.e("Movie Cast Error", t.getMessage());
            }
        });
        return listCast;
    }
}
