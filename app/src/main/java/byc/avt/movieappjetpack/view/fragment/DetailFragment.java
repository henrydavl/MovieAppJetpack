package byc.avt.movieappjetpack.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import byc.avt.movieappjetpack.R;
import byc.avt.movieappjetpack.adapter.CastAdapter;
import byc.avt.movieappjetpack.databinding.FragmentDetailBinding;
import byc.avt.movieappjetpack.model.Genre;
import byc.avt.movieappjetpack.model.Movie;
import byc.avt.movieappjetpack.view.MainActivity;
import byc.avt.movieappjetpack.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    @BindView(R.id.pb_detail)
    ProgressBar pb_detail;
    @BindView(R.id.layerHide)
    ImageView layerHide;
    @BindView(R.id.img_fav)
    ImageView addFav;
    @BindView(R.id.detail_genre)
    TextView tv_genre;
    @BindView(R.id.tv_addFav)
    TextView tv_addFav;
    @BindView(R.id.rv_cast)
    RecyclerView rvCast;

    private DetailViewModel viewModel;
    private CastAdapter castAdapter;
    private FragmentDetailBinding binding;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        this.binding = binding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(false);
        showLoading(true);
        rvCast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        castAdapter = new CastAdapter(getActivity());
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        if (getArguments() != null) {
            Movie m = DetailFragmentArgs.fromBundle(getArguments()).getItem();
            init(m);
            viewModel.getData(m.getId_movie());
        }

        observeViewModel();
    }

    private void observeViewModel() {

        viewModel.listGenres.observe(requireActivity(), genres -> {
            if (genres != null) {
                for (int i = 0; i < genres.size(); i++) {
                    Genre g = genres.get(i);
                    if (i < genres.size() - 1) {
                        tv_genre.append(g.getName() + " | ");
                    } else {
                        tv_genre.append(g.getName());
                    }
                }
            }
        });

        viewModel.listCast.observe(requireActivity(), casts -> {
            if (casts != null) {
                castAdapter.setCasts(casts);
                castAdapter.notifyDataSetChanged();
                rvCast.setAdapter(castAdapter);
            }
        });

        viewModel.movieError.observe(requireActivity(), error -> {
            if (error != null) {
                if (error) Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.loading.observe(requireActivity(), loading -> {
            if (loading != null) {
                showLoading(loading);
            }
        });
    }

    private void init(Movie m) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(m.getTitle());
        showLoading(false);
        binding.setMovieData(m);
    }

    private void showLoading(Boolean state) {
        if (state) {
            pb_detail.setVisibility(View.VISIBLE);
            layerHide.setVisibility(View.VISIBLE);
        } else {
            pb_detail.setVisibility(View.GONE);
            layerHide.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
            layerHide.setVisibility(View.GONE);
            binding.detailCover.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_animation));
            binding.detailPoster.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_transition));
        }
    }
}
