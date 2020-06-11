package byc.avt.movieappjetpack.util;

import android.view.View;

import byc.avt.movieappjetpack.model.Movie;

public interface ItemClickListener {
    void onMovieClicked(View view, Movie movie);
}
