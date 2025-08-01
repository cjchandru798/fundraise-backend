package com.example.fundraise.controller;

import com.example.fundraise.config.JwtUtil;
import com.example.fundraise.dto.CreateAnnouncementRequest;
import com.example.fundraise.entity.Admin;
import com.example.fundraise.entity.Announcement;
import com.example.fundraise.repository.AdminRepository;
import com.example.fundraise.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final JwtUtil jwtUtil;
    private final AdminRepository adminRepository;

    @PostMapping("/post")
    public ResponseEntity<String> postAnnouncement(@RequestBody CreateAnnouncementRequest request, HttpServletRequest httpRequest) {
        String email = jwtUtil.extractEmail(httpRequest.getHeader("Authorization").substring(7));
        Admin admin = adminRepository.findByEmail(email).orElseThrow();

        if (!admin.isSuperAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only super admins can post announcements");
        }

        announcementService.post(request.getTitle(), request.getMessage());
        return ResponseEntity.ok("Announcement posted");
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAnnouncements() {
        return ResponseEntity.ok(announcementService.getAll());
    }
}
