package byc.avt.movieappjetpack.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import byc.avt.movieappjetpack.BuildConfig;

public class ImageLoader {

    private static void loader(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(BuildConfig.BASE_IMAGE_URL + url)
                .into(view);
    }

    @BindingAdapter("android:movieCover")
    public static void loadMovieCover(ImageView view, String url) {
        loader(view, url);
    }

    @BindingAdapter("android:moviePoster")
    public static void loadMoviePoster(ImageView view, String url) {
        loader(view, url);
    }

    @BindingAdapter("android:castImage")
    public static void loadCastImage(ImageView view, String url) {
        loader(view, url);
    }
}
