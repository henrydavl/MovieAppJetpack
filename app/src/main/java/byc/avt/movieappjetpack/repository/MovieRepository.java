package byc.avt.movieappjetpack.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import byc.avt.movieappjetpack.BuildConfig;
import byc.avt.movieappjetpack.model.Cast;
import byc.avt.movieappjetpack.model.CastResponse;
import byc.avt.movieappjetpack.model.Genre;
import byc.avt.movieappjetpack.model.GenreResponse;
import byc.avt.movieappjetpack.model.Movie;
import byc.avt.movieappjetpack.model.MovieResponse;
import byc.avt.movieappjetpack.util.ApiService;
import byc.avt.movieappjetpack.util.RetrofitService;
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
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
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
            public void onResponse(@NonNull Call<GenreResponse> call,@NonNull Response<GenreResponse> response) {
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
            public void onResponse(@NonNull Call<CastResponse> call,@NonNull Response<CastResponse> response) {
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
