package com.example.fundraise.controller;
import com.example.fundraise.dto.InternDashboardResponse;
import com.example.fundraise.service.InternDashboardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/intern")
@RequiredArgsConstructor
public class InternController {

    private final InternDashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<InternDashboardResponse> getDashboard(HttpServletRequest request) {
        return ResponseEntity.ok(dashboardService.getDashboard(request));
    }
}
