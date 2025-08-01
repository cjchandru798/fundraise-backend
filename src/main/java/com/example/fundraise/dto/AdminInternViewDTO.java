package com.example.fundraise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminInternViewDTO {
    private Long id;
    private String name;
    private String email;
    private String college;
    private String city;
    private int totalAmount;
}
