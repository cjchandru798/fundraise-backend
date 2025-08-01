package com.example.fundraise.controller;

import com.example.fundraise.config.JwtUtil;
import com.example.fundraise.dto.DonationResponse;
import com.example.fundraise.dto.InternDashboardResponse;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.entity.Notification;
import com.example.fundraise.repository.InternRepository;
import com.example.fundraise.repository.NotificationRepository;
import com.example.fundraise.service.DonationService;
import com.example.fundraise.service.InternDashboardService;
import com.example.fundraise.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intern")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class InternController {

    private final InternDashboardService dashboardService;
    private final DonationService donationService;
    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;
    private final InternRepository internRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<InternDashboardResponse> getDashboard(HttpServletRequest request) {
        InternDashboardResponse response = dashboardService.getDashboard(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<DonationResponse>> getTransactions(HttpServletRequest request) {
        String token = extractToken(request);
        String email = jwtUtil.extractEmail(token);
        List<DonationResponse> transactions = donationService.getDonationsForIntern(email);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(HttpServletRequest request) {
        String token = extractToken(request);
        String email = jwtUtil.extractEmail(token);
        Intern intern = internRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Intern not found with email: " + email));
        List<Notification> notifications = notificationService.getUnreadNotifications(intern);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/notifications/mark-read")
    public ResponseEntity<String> markNotificationsRead(HttpServletRequest request) {
        String token = extractToken(request);
        String email = jwtUtil.extractEmail(token);
        Intern intern = internRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Intern not found with email: " + email));
        notificationService.markAllAsRead(intern);
        return ResponseEntity.ok("All notifications marked as read.");
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }
}
