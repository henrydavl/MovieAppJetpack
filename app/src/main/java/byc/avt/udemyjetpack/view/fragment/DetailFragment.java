package byc.avt.udemyjetpack.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import byc.avt.udemyjetpack.BuildConfig;
import byc.avt.udemyjetpack.R;
import byc.avt.udemyjetpack.adapter.CastAdapter;
import byc.avt.udemyjetpack.model.Cast;
import byc.avt.udemyjetpack.model.Genre;
import byc.avt.udemyjetpack.model.Movie;
import byc.avt.udemyjetpack.view.MainActivity;
import byc.avt.udemyjetpack.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    @BindView(R.id.pb_detail)
    ProgressBar pb_detail;
    @BindView(R.id.layerHide)
    ImageView layerHide;
    @BindView(R.id.detail_cover)
    ImageView detailCover;
    @BindView(R.id.detail_poster)
    ImageView detailPoster;
    @BindView(R.id.img_fav)
    ImageView addFav;
    @BindView(R.id.detail_title)
    TextView tv_title;
    @BindView(R.id.detail_description)
    TextView tv_description;
    @BindView(R.id.detail_popular)
    TextView tv_popular;
    @BindView(R.id.detail_genre)
    TextView tv_genre;
    @BindView(R.id.tv_addFav)
    TextView tv_addFav;
    @BindView(R.id.rv_cast)
    RecyclerView rvCast;

    private CastAdapter castAdapter;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        rvCast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        castAdapter = new CastAdapter(getActivity());
        DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        if (getArguments() != null) {
            Movie m = DetailFragmentArgs.fromBundle(getArguments()).getItem();
            viewModel.getGenre(m.getId_movie()).observe(getActivity(), loadGenre);
            viewModel.getCast(m.getId_movie()).observe(getActivity(), loadCast);
            init(m);
        }
    }

    private Observer<List<Genre>> loadGenre = new Observer<List<Genre>>() {
        @Override
        public void onChanged(List<Genre> movieGenres) {
            if (movieGenres != null) {
                for (int i = 0; i < movieGenres.size(); i++) {
                    Genre g = movieGenres.get(i);
                    if (i < movieGenres.size() - 1){
                        tv_genre.append(g.getName() + " | ");
                    } else {
                        tv_genre.append(g.getName());
                    }
                }
            }
        }
    };

    private Observer<List<Cast>> loadCast = new Observer<List<Cast>>() {
        @Override
        public void onChanged(@Nullable List<Cast> casts) {
            if (casts != null){
                castAdapter.setCasts(casts);
                castAdapter.notifyDataSetChanged();
                rvCast.setAdapter(castAdapter);
                showLoading(false);
            }
        }
    };

    private void init(Movie m){
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(m.getTitle());
        Glide.with(getActivity()).load(BuildConfig.BASE_IMAGE_URL + m.getCover()).into(detailCover);
        Glide.with(getActivity()).load(BuildConfig.BASE_IMAGE_URL + m.getPoster()).into(detailPoster);
        tv_title.setText(m.getTitle());
        tv_popular.setText(m.getPopularity());
        tv_description.setText(m.getDescription());
        showLoading(false);
    }

    private void showLoading(Boolean state) {
        if (state) {
            pb_detail.setVisibility(View.VISIBLE);
            layerHide.setVisibility(View.VISIBLE);
        } else {
            pb_detail.setVisibility(View.GONE);
            layerHide.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
            layerHide.setVisibility(View.GONE);
            detailCover.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_animation));
            detailPoster.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_transition));
        }
    }
}
