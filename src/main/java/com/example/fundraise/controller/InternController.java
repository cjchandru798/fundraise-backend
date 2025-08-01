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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/intern")
@RequiredArgsConstructor
public class InternController {

    private final InternDashboardService dashboardService;
    private final DonationService donationService;
    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;
    private final InternRepository internRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<InternDashboardResponse> getDashboard(HttpServletRequest request) {
        return ResponseEntity.ok(dashboardService.getDashboard(request));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<DonationResponse>> getTransactions(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(donationService.getDonationsForIntern(email));
    }
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        Intern intern = internRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(notificationService.getUnreadNotifications(intern));
    }

    @PostMapping("/notifications/mark-read")
    public ResponseEntity<String> markNotificationsRead(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        Intern intern = internRepository.findByEmail(email).orElseThrow();
        notificationService.markAllAsRead(intern);
        return ResponseEntity.ok("All notifications marked as read.");
    }

}
