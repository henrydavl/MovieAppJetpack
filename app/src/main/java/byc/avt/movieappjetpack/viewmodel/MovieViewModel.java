package byc.avt.movieappjetpack.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import byc.avt.movieappjetpack.db.MovieDao;
import byc.avt.movieappjetpack.db.MovieDatabase;
import byc.avt.movieappjetpack.model.Movie;
import byc.avt.movieappjetpack.model.MovieResponse;
import byc.avt.movieappjetpack.util.RetrofitService;
import byc.avt.movieappjetpack.util.SharedPreferencesHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieViewModel extends AndroidViewModel {

    private RetrofitService apiService = new RetrofitService();
    private CompositeDisposable disposable = new CompositeDisposable();
    private AsyncTask<List<Movie>, Void, List<Movie>> insertTask;
    private AsyncTask<Void, Void, List<Movie>> retrieveTask;

    public MutableLiveData<Boolean> movieError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<List<Movie>> listMovies = new MutableLiveData<>();

    private SharedPreferencesHelper preferencesHelper = SharedPreferencesHelper.getInstance(getApplication());

    public MovieViewModel(Application application) {
        super(application);
    }

    public void refresh() {
        long updateTime = preferencesHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;
        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            getMovieFromDatabase();
        } else {
            getMovieDiscovery();
        }
    }

    public void forceRefresh(){
        getMovieDiscovery();
    }

    private void getMovieDiscovery() {
        loading.setValue(true);
        disposable.add(
                apiService.getMovies()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movieResponse) {
                                insertTask = new InsertMoviesTask();
                                insertTask.execute(movieResponse.getResults());
                                Toast.makeText(getApplication(), "Movie retrieved from endpoint", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                setError(true);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void getMovieFromDatabase() {
        loading.setValue(true);
        retrieveTask = new RetrieveMovie();
        retrieveTask.execute();
    }

    private void retrieveMovie(List<Movie> movies) {
        listMovies.postValue(movies);
        setError(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }

    private void setError(boolean state) {
        if (state) {
            movieError.setValue(true);
        } else {
            movieError.setValue(false);
        }
        loading.setValue(false);
    }

    private class InsertMoviesTask extends AsyncTask<List<Movie>, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(List<Movie>... lists) {
            List<Movie> list = lists[0];
            MovieDao dao = MovieDatabase.getInstance(getApplication()).movieDao();
            dao.deleteAllMovie();

            ArrayList<Movie> newMovie = new ArrayList<>(list);
            List<Long> result = dao.insertAll(newMovie.toArray(new Movie[0]));

            for (int i = 0; i < list.size(); i++) {
                list.get(i).uId = result.get(i).intValue();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            retrieveMovie(movies);
            preferencesHelper.saveUpdateTime(System.nanoTime());
        }
    }

    private class RetrieveMovie extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return MovieDatabase.getInstance(getApplication()).movieDao().getAllMovie();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            retrieveMovie(movies);
            Toast.makeText(getApplication(), "Movie retrieved from database", Toast.LENGTH_SHORT).show();
        }
    }

}
