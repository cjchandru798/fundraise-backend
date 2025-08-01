package com.example.fundraise.controller;

import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import com.example.fundraise.repository.DonationRepository;
import com.example.fundraise.repository.InternRepository;
import com.example.fundraise.util.CsvExportUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/export")
@RequiredArgsConstructor
public class AdminExportController {

    private final InternRepository internRepository;
    private final DonationRepository donationRepository;
    private final CsvExportUtil csvExportUtil;

    @GetMapping("/interns")
    public void exportInterns(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=interns.csv");

        List<Intern> interns = internRepository.findAll();
        csvExportUtil.writeInternCsv(response.getWriter(), interns);
    }

    @GetMapping("/donations")
    public void exportDonations(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=donations.csv");

        List<Donation> donations = donationRepository.findAll();
        csvExportUtil.writeDonationCsv(response.getWriter(), donations);
    }
}
