package com.example.fundraise.repository;

import com.example.fundraise.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternRepository extends JpaRepository<Intern, Long> {
    Optional<Intern> findByEmail(String email);
    Optional<Intern> findByReferralCode(String referralCode);
}
