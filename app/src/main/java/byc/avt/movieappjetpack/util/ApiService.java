package byc.avt.movieappjetpack.util;

import byc.avt.movieappjetpack.model.CastResponse;
import byc.avt.movieappjetpack.model.GenreResponse;
import byc.avt.movieappjetpack.model.MovieResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie") // get movies collection
    Single<MovieResponse> getMovie(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}") // get details (if needed) and genres of specific movie
    Single<GenreResponse> getMovieGenre(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits") // get casts of specific movie
    Single<CastResponse> getCastMovie(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
