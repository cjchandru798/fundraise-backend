package com.example.fundraise.service;

import com.example.fundraise.dto.LeaderboardEntry;
import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final DonationRepository donationRepository;
    private final InternRepository internRepository;

    public List<LeaderboardEntry> getLeaderboard(String filter) {
        LocalDate today = LocalDate.now();
        List<Donation> allDonations = donationRepository.findAll();

        // filter by date
        List<Donation> filtered = switch (filter.toLowerCase()) {
            case "daily" -> allDonations.stream()
                    .filter(d -> d.getDate().equals(today))
                    .toList();
            case "weekly" -> allDonations.stream()
                    .filter(d -> d.getDate().isAfter(today.minusDays(7)))
                    .toList();
            default -> allDonations; // "all"
        };

        // sum amounts by intern
        Map<Intern, Integer> totals = new HashMap<>();
        for (Donation d : filtered) {
            totals.put(d.getIntern(), totals.getOrDefault(d.getIntern(), 0) + d.getAmount());
        }

        // sort and rank
        List<Map.Entry<Intern, Integer>> sorted = totals.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .toList();

        List<LeaderboardEntry> result = new ArrayList<>();
        int rank = 1;
        for (Map.Entry<Intern, Integer> entry : sorted) {
            result.add(new LeaderboardEntry(rank++, entry.getKey().getName(), entry.getValue()));
        }

        return result;
    }
}
