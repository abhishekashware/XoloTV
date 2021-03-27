package com.example.xolotv.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xolotv.R;
import com.example.xolotv.adapters.TVShowsAdapter;
import com.example.xolotv.databinding.ActivityMainBinding;
import com.example.xolotv.listeners.TVShowsListener;
import com.example.xolotv.model.TVShow;
import com.example.xolotv.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization() {
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows, this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePage) {
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        activityMainBinding.imageWatchList.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), WatchlistActivity.class)));
        activityMainBinding.imageSearch.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this, mostPopularTVShowsResponse -> {
                    toggleLoading();
                    if (mostPopularTVShowsResponse != null) {
                        totalAvailablePage = mostPopularTVShowsResponse.getTotalPages();
                        if (mostPopularTVShowsResponse.getTvShows() != null) {
                            int oldCount = tvShows.size();
                            tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                            tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size());
                        }
                    }
                }
        );
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            activityMainBinding.setIsLoading(
                    !(activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()));
        } else {
            activityMainBinding.setIsLoadingMore(
                    !(activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()));
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}