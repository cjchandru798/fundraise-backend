package com.example.fundraise.service;

import com.example.fundraise.dto.AdminInternViewDTO;
import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminInternService {

    private final InternRepository internRepository;
    private final DonationRepository donationRepository;

    public List<AdminInternViewDTO> getAllInterns() {
        return internRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AdminInternViewDTO> filterInterns(String city, String college, Integer minAmount) {
        return internRepository.findAll().stream()
                .filter(i -> city == null || i.getCity().equalsIgnoreCase(city))
                .filter(i -> college == null || i.getCollege().equalsIgnoreCase(college))
                .filter(i -> {
                    int total = donationRepository.findByIntern(i).stream().mapToInt(Donation::getAmount).sum();
                    return minAmount == null || total >= minAmount;
                })
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AdminInternViewDTO getInternDetails(Long id) {
        Intern intern = internRepository.findById(id).orElseThrow(() -> new RuntimeException("Intern not found"));
        return mapToDTO(intern);
    }

    private AdminInternViewDTO mapToDTO(Intern intern) {
        int totalAmount = donationRepository.findByIntern(intern).stream().mapToInt(Donation::getAmount).sum();
        return new AdminInternViewDTO(
                intern.getId(),
                intern.getName(),
                intern.getEmail(),
                intern.getCollege(),
                intern.getCity(),
                totalAmount
        );
    }
}
