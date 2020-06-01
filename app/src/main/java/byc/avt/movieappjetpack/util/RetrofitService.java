package byc.avt.movieappjetpack.util;

import byc.avt.movieappjetpack.BuildConfig;
import byc.avt.movieappjetpack.model.CastResponse;
import byc.avt.movieappjetpack.model.GenreResponse;
import byc.avt.movieappjetpack.model.MovieResponse;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    //   private static Retrofit retrofit;
    private ApiService api;
    private String apiKey = BuildConfig.API_KEY;

    // Option 1 with no RxJava
//   public static <S> S createService(Class<S> serviceClass) {
//       if (retrofit == null) {
//           retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//       }
//       return retrofit.create(serviceClass);
//   }

    // Option 1 with RxJava
    public RetrofitService() {
        api = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //convert movie response to single observable
                .build()
                .create(ApiService.class);
    }

    public Single<MovieResponse> getMovies() {
        return api.getMovie(apiKey);
    }

    public Single<GenreResponse> getMovieGenre(int movieId){
        return api.getMovieGenre(movieId, apiKey);
    }

    public Single<CastResponse> getMovieCast(int movieId){
        return api.getCastMovie(movieId, apiKey);
    }
}
