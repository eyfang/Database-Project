package com.example.cs4750.repositories;

import com.example.cs4750.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    @Query("SELECT MAX(w.watchListId) FROM Watchlist w")
    Optional<Integer> findMaxWatchlistId();

    List<Watchlist> findByUserId(Integer userId);
}