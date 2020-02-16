package byc.avt.movieappjetpack.util;

import byc.avt.movieappjetpack.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
   private static Retrofit retrofit;

   public static <S> S createService(Class<S> serviceClass) {
       if (retrofit == null) {
           retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
       }
       return retrofit.create(serviceClass);
   }

//    public RetrofitService() {
//        api = new Retrofit.Builder()
//                .baseUrl(BuildConfig.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//    }
//
//    public Single<ArrayList<Movie>> getMovies() {
//        return api.getMovie();
//    }
}
