package com.example.fundraise.controller;

import com.example.fundraise.dto.AdminLoginRequest;
import com.example.fundraise.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminService.login(request));
    }
}
