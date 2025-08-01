package com.example.fundraise.controller;

import com.example.fundraise.dto.AdminInternViewDTO;
import com.example.fundraise.service.AdminInternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/interns")
@RequiredArgsConstructor
public class AdminInternController {

    private final AdminInternService adminInternService;

    @GetMapping
    public ResponseEntity<List<AdminInternViewDTO>> getAllInterns() {
        return ResponseEntity.ok(adminInternService.getAllInterns());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AdminInternViewDTO>> filterInterns(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) Integer minAmount
    ) {
        return ResponseEntity.ok(adminInternService.filterInterns(city, college, minAmount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminInternViewDTO> getInternById(@PathVariable Long id) {
        return ResponseEntity.ok(adminInternService.getInternDetails(id));
    }
}
