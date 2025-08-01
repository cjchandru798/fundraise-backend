package com.example.fundraise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DonationResponse {
    private String donorName;
    private int amount;
    private LocalDate date;
}
