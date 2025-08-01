package com.example.fundraise.controller;

import com.example.fundraise.dto.AnalyticsSummaryDTO;
import com.example.fundraise.dto.DailyDataDTO;
import com.example.fundraise.dto.TopInternDTO;
import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AdminAnalyticsController {

    private final DonationRepository donationRepo;
    private final InternRepository internRepo;

    // 🔹 Dashboard summary: Total amount, total donors, total interns
    @GetMapping("/summary")
    public AnalyticsSummaryDTO getSummary() {
        int totalAmount = donationRepo.findAll().stream()
                .mapToInt(Donation::getAmount).sum();

        int totalDonors = (int) donationRepo.findAll().stream()
                .map(Donation::getDonorName)
                .filter(name -> name != null && !name.isBlank())
                .distinct()
                .count();

        int totalInterns = internRepo.findAll().size();

        AnalyticsSummaryDTO dto = new AnalyticsSummaryDTO();
        dto.setTotalAmount(totalAmount);
        dto.setTotalDonors(totalDonors);
        dto.setTotalInterns(totalInterns);
        return dto;
    }

    // 🔹 Daily donation totals for the last 7 days (by LocalDate)
    @GetMapping("/daily")
    public List<DailyDataDTO> getLast7Days() {
        LocalDate today = LocalDate.now();

        return IntStream.rangeClosed(0, 6)
                .mapToObj(i -> {
                    LocalDate date = today.minusDays(i);
                    int totalAmount = donationRepo.findByDate(date).stream()
                            .mapToInt(Donation::getAmount)
                            .sum();
                    return new DailyDataDTO(date.toString(), totalAmount);
                })
                .sorted(Comparator.comparing(DailyDataDTO::getDate))
                .collect(Collectors.toList());
    }


    // 🔹 Top 5 interns this week based on donation amount
    @GetMapping("/top")
    public List<TopInternDTO> getTopInternsThisWeek() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);

        Map<Intern, Integer> donationByIntern = new HashMap<>();

        for (Donation donation : donationRepo.findByDateAfter(startOfWeek.minusDays(1))) {
            Intern intern = donation.getIntern();
            donationByIntern.merge(intern, donation.getAmount(), Integer::sum);
        }

        return donationByIntern.entrySet().stream()
                .sorted(Map.Entry.<Intern, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new TopInternDTO(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

