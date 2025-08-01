package com.example.fundraise.service;

import com.example.fundraise.entity.Intern;
import com.example.fundraise.entity.Notification;
import com.example.fundraise.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(Intern intern, String message) {
        Notification n = new Notification();
        n.setMessage(message);
        n.setTimestamp(LocalDateTime.now());
        n.setIntern(intern);
        n.setRead(false);
        notificationRepository.save(n);
    }

    public List<Notification> getUnreadNotifications(Intern intern) {
        return notificationRepository.findByInternAndReadFalseOrderByTimestampDesc(intern);
    }

    public void markAllAsRead(Intern intern) {
        List<Notification> unread = getUnreadNotifications(intern);
        for (Notification n : unread) {
            n.setRead(true);
        }
        notificationRepository.saveAll(unread);
    }
}
