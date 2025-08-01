package com.example.fundraise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private boolean read = false;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "intern_id")
    private Intern intern;
}
