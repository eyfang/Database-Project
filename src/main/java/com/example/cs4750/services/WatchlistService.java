package com.example.cs4750.services;

import com.example.cs4750.models.Watchlist;
import com.example.cs4750.repositories.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;

    public Watchlist addToWatchlist(Integer userId, Integer movieId) {
        Watchlist watchlist = new Watchlist();
        Integer newWatchlistId = watchlistRepository.findMaxWatchlistId().orElse(0) + 1;
        watchlist.setWatchListId(newWatchlistId);
        watchlist.setUserId(userId);
        watchlist.setMovieId(movieId);
        return watchlistRepository.save(watchlist);
    }

    public List<Watchlist> getWatchlistByUserId(Integer userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public void removeFromWatchlist(Integer watchlistId) {
        watchlistRepository.deleteById(watchlistId);
    }
}