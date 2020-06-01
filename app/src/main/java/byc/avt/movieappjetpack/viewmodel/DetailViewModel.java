package byc.avt.movieappjetpack.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import byc.avt.movieappjetpack.model.Cast;
import byc.avt.movieappjetpack.model.CastResponse;
import byc.avt.movieappjetpack.model.Genre;
import byc.avt.movieappjetpack.model.GenreResponse;
import byc.avt.movieappjetpack.util.RetrofitService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {

    public MutableLiveData<List<Genre>> listGenres = new MutableLiveData<>();
    public MutableLiveData<List<Cast>> listCast = new MutableLiveData<>();

    private RetrofitService apiService = new RetrofitService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Boolean> movieError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public DetailViewModel(Application application) {
        super(application);
    }

    public void getData(String id) {
        loading.setValue(true);
        getGenre(id);
        getCast(id);
    }

    private void getGenre(String id) {
        disposable.add(
                apiService.getMovieGenre(Integer.parseInt(id))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<GenreResponse>() {
                            @Override
                            public void onSuccess(GenreResponse genreResponse) {
                                listGenres.postValue(genreResponse.getGenres());
                                setError(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                setError(true);
                            }
                        })
        );
    }

    private void getCast(String id) {
        disposable.add(apiService.getMovieCast(Integer.parseInt(id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CastResponse>() {
                    @Override
                    public void onSuccess(CastResponse castResponse) {
                        listCast.postValue(castResponse.getCast());
                        setError(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setError(true);
                    }
                })
        );
    }

    private void setError(boolean state) {
        if (state) {
            movieError.setValue(true);
        } else {
            movieError.setValue(false);
        }
        loading.setValue(false);
    }
}
