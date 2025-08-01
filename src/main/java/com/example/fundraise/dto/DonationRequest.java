package com.example.fundraise.dto;

import lombok.Data;

@Data
public class DonationRequest {
    private String donorName;
    private int amount;
}
