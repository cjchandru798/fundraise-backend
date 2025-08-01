package com.example.fundraise.repository;

import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByIntern(Intern intern);
    void deleteAll();
        List<Donation> findByDate(LocalDate date);
        List<Donation> findByDateBetween(LocalDate start, LocalDate end);
        List<Donation> findByDateAfter(LocalDate date);

}
