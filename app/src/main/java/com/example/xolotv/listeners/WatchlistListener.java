package com.example.xolotv.listeners;

import com.example.xolotv.model.TVShow;

public interface WatchlistListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchlist(TVShow tvShow, int position);
}
