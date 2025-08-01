package com.example.fundraise.controller;

import com.example.fundraise.dto.LeaderboardEntry;
import com.example.fundraise.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard(
            @RequestParam(defaultValue = "all") String filter) {
        return ResponseEntity.ok(leaderboardService.getLeaderboard(filter));
    }
}
