package com.example.fundraise.repository;

import com.example.fundraise.entity.Intern;
import com.example.fundraise.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByInternAndReadFalseOrderByTimestampDesc(Intern intern);
}
