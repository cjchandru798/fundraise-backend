package com.example.fundraise.repository;

import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByIntern(Intern intern);
    void deleteAll();

}
