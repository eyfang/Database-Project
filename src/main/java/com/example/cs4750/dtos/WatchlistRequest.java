package com.example.cs4750.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WatchlistRequest {
    private Integer userId;
    private Integer movieId;

}
