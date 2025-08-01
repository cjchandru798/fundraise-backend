package com.example.fundraise.service;


import com.example.fundraise.config.JwtUtil;
import com.example.fundraise.dto.InternDashboardResponse;
import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternDashboardService {

    private final InternRepository internRepository;
    private final DonationRepository donationRepository;
    private final JwtUtil jwtUtil;

    public InternDashboardResponse getDashboard(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);

        Intern intern = internRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Intern not found"));

        List<Donation> donations = donationRepository.findByIntern(intern);

        int totalAmount = donations.stream().mapToInt(Donation::getAmount).sum();
        int donorCount = donations.size();

        String badge = getBadge(totalAmount);

        return new InternDashboardResponse(
                "Welcome, " + intern.getName() + "!",
                totalAmount,
                donorCount,
                intern.getReferralCode(),
                "https://yourdomain.com/donate/" + intern.getReferralCode(),
                badge
        );
    }

    private String getBadge(int total) {
        if (total >= 2000) return "🎖️ Gold";
        else if (total >= 1000) return "🥈 Silver";
        else if (total >= 500) return "🥉 Bronze";
        else return "🕐 Keep Going!";
    }
}
