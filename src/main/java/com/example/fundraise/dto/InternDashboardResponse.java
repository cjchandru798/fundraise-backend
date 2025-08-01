package com.example.fundraise.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternDashboardResponse {
    private String greeting;
    private int totalAmountRaised;
    private int totalDonors;
    private String referralCode;
    private String donationLink;
    private String badge;
}
