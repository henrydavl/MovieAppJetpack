package byc.avt.udemyjetpack.util;

import byc.avt.udemyjetpack.model.CastResponse;
import byc.avt.udemyjetpack.model.GenreResponse;
import byc.avt.udemyjetpack.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie") // get movies collection
    Call<MovieResponse> getMovie(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}") // get details (if needed) and genres of specific movie
    Call<GenreResponse> getMovieGenre(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits") // get casts of specific movie
    Call<CastResponse> getCastMovie(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
