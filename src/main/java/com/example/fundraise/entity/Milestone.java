package com.example.fundraise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount; // e.g., 500, 1000
    private String label; // e.g., Bronze, Silver
    private String icon;  // optional: could be emoji or icon name
}
