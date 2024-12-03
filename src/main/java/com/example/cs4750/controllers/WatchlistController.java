package com.example.cs4750.controllers;

import com.example.cs4750.dtos.WatchlistRequest;
import com.example.cs4750.models.Watchlist;
import com.example.cs4750.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlists")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<?> addToWatchlist(@RequestBody WatchlistRequest request) {
        try {
            Watchlist watchlist = watchlistService.addToWatchlist(request.getUserId(), request.getMovieId());
            return ResponseEntity.ok(watchlist);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{watchListId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable Integer watchListId) {
        try {
            watchlistService.removeFromWatchlist(watchListId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}