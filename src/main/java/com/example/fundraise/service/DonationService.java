package com.example.fundraise.service;

import com.example.fundraise.dto.DonationRequest;
import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final InternRepository internRepository;
    private final DonationRepository donationRepository;

    public String submitDonation(String referralCode, DonationRequest request) {
        Intern intern = internRepository.findByReferralCode(referralCode)
                .orElseThrow(() -> new RuntimeException("Invalid referral code"));

        Donation donation = new Donation();
        donation.setDonorName(request.getDonorName());
        donation.setAmount(request.getAmount());
        donation.setDate(LocalDate.now());
        donation.setIntern(intern);

        donationRepository.save(donation);
        return "Thank you for your donation!";
    }
}
