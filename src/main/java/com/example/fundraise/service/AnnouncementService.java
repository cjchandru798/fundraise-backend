package com.example.fundraise.service;

import com.example.fundraise.entity.Announcement;
import com.example.fundraise.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository repo;

    public void post(String title, String msg) {
        repo.save(new Announcement(null, title, msg, LocalDateTime.now()));
    }

    public List<Announcement> getAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}
