package com.example.fundraise.controller;

import com.example.fundraise.dto.MilestoneDTO;
import com.example.fundraise.entity.Admin;
import com.example.fundraise.entity.Milestone;
import com.example.fundraise.repository.AdminRepository;
import com.example.fundraise.service.MilestoneService;
import com.example.fundraise.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;
    private final JwtUtil jwtUtil;
    private final AdminRepository adminRepository;

    private void verifySuperAdmin(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        Admin admin = adminRepository.findByEmail(email).orElseThrow();
        if (!admin.isSuperAdmin())
            throw new RuntimeException("Not authorized");
    }

    @PostMapping
    public ResponseEntity<String> addMilestone(@RequestBody MilestoneDTO dto, HttpServletRequest request) {
        verifySuperAdmin(request);
        milestoneService.addMilestone(dto);
        return ResponseEntity.ok("Milestone added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMilestone(@PathVariable Long id, HttpServletRequest request) {
        verifySuperAdmin(request);
        milestoneService.deleteMilestone(id);
        return ResponseEntity.ok("Milestone deleted");
    }

    @GetMapping
    public ResponseEntity<List<Milestone>> getAllMilestones() {
        return ResponseEntity.ok(milestoneService.getAllMilestones());
    }
}
