package com.example.fundraise.controller;

import com.example.fundraise.dto.DonationRequest;
import com.example.fundraise.service.DonationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donate")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class DonationController {

    private final DonationService donationService;

    @PostMapping("/{referralCode}")
    public ResponseEntity<?> donate(
            @PathVariable String referralCode,
            @RequestBody DonationRequest request) {
        String msg = donationService.submitDonation(referralCode, request);
        return ResponseEntity.ok().body("{\"message\": \"" + msg + "\"}");
    }
}
