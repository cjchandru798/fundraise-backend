package com.example.fundraise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyDataDTO {
    private String date;
    private int totalAmount;
}
