package com.example.xolotv.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.xolotv.repositories.MostPopularTVShowsRepository;
import com.example.xolotv.responses.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private final MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel() {
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page) {
        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }
}
