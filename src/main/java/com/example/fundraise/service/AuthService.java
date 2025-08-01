package com.example.fundraise.service;

import com.example.fundraise.config.JwtUtil;
import com.example.fundraise.dto.AuthResponse;
import com.example.fundraise.dto.LoginRequest;
import com.example.fundraise.dto.SignupRequest;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InternRepository internRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        if (internRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String referralCode = "INT" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        Intern intern = new Intern();
        intern.setName(request.getName());
        intern.setEmail(request.getEmail());
        intern.setPassword(encodedPassword);
        intern.setCity(request.getCity());
        intern.setCollege(request.getCollege());
        intern.setReferralCode(referralCode);

        internRepository.save(intern);

        String token = jwtUtil.generateToken(intern.getEmail());
        return new AuthResponse(token, "Signup successful");
    }

    public AuthResponse login(LoginRequest request) {
        Intern intern = internRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), intern.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(intern.getEmail());
        return new AuthResponse(token, "Login successful");
    }
}
