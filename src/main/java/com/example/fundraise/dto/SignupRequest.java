package com.example.fundraise.dto;
import lombok.*;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String city;
    private String college;
}
