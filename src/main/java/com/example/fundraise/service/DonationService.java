package com.example.fundraise.service;

import com.example.fundraise.dto.DonationRequest;
import com.example.fundraise.dto.DonationResponse;
import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final InternRepository internRepository;
    private final DonationRepository donationRepository;
    private final NotificationService notificationService;

    public List<DonationResponse> getDonationsForIntern(String email) {
        Intern intern = internRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Intern not found"));

        return donationRepository.findByIntern(intern).stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .map(d -> new DonationResponse(d.getDonorName(), d.getAmount(), d.getDate()))
                .toList();
    }

    public String submitDonation(String referralCode, DonationRequest request) {
        Intern intern = internRepository.findByReferralCode(referralCode)
                .orElseThrow(() -> new RuntimeException("Invalid referral code"));

        Donation donation = new Donation();
        donation.setDonorName(request.getDonorName());
        donation.setAmount(request.getAmount());
        donation.setDate(LocalDate.now());
        donation.setIntern(intern);

        donationRepository.save(donation);

        // 🔔 Send donation notification
        notificationService.sendNotification(
                intern,
                "🎉 You received a ₹" + request.getAmount() + " donation!"
        );

        // 🎯 Check for badge thresholds
        int newTotal = donationRepository.findByIntern(intern)
                .stream().mapToInt(Donation::getAmount).sum();

        String badgeMsg = switch (newTotal) {
            case 500 -> "🥉 You’ve earned the Bronze Badge!";
            case 1000 -> "🥈 You’ve earned the Silver Badge!";
            case 2000 -> "🥇 You’ve earned the Gold Badge!";
            default -> null;
        };

        if (badgeMsg != null) {
            notificationService.sendNotification(intern, badgeMsg);
        }

        return "Thank you for your donation!";
    }
}
