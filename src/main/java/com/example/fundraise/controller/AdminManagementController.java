package com.example.fundraise.controller;

import com.example.fundraise.config.JwtUtil;
import com.example.fundraise.dto.CreateAdminRequest;
import com.example.fundraise.entity.Admin;
import com.example.fundraise.repository.AdminRepository;
import com.example.fundraise.repository.DonationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class AdminManagementController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final DonationRepository donationRepository;

    private void verifySuperAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        Admin admin = adminRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin not found")
        );

        if (!admin.isSuperAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not a Super Admin");
        }
    }

    @PostMapping("/reset-leaderboard")
    public ResponseEntity<String> resetLeaderboard(HttpServletRequest request) {
        verifySuperAdmin(request);
        donationRepository.deleteAll();
        return ResponseEntity.ok("Leaderboard reset — all donations cleared.");
    }

    @PostMapping("/add-admin")
    public ResponseEntity<String> addAdmin(@RequestBody CreateAdminRequest requestBody, HttpServletRequest request) {
        verifySuperAdmin(request);

        // Prevent duplicate email registration
        if (adminRepository.findByEmail(requestBody.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin with this email already exists.");
        }

        Admin newAdmin = new Admin(
                requestBody.getEmail(),
                passwordEncoder.encode(requestBody.getPassword()),
                requestBody.getName()
        );
        newAdmin.setSuperAdmin(false);  // ✅ default

        adminRepository.save(newAdmin);
        return ResponseEntity.ok("Admin added successfully");
    }

    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id, HttpServletRequest request) {
        verifySuperAdmin(request);
        if (!adminRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }

        adminRepository.deleteById(id);
        return ResponseEntity.ok("Admin deleted");
    }

    @GetMapping("/all-admins")
    public ResponseEntity<List<Admin>> getAllAdmins(HttpServletRequest request) {
        verifySuperAdmin(request);
        return ResponseEntity.ok(adminRepository.findAll());
    }
}
