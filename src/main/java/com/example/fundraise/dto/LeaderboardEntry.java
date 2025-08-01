package com.example.fundraise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaderboardEntry {
    private int rank;
    private String name;
    private int amount;
}
