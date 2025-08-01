package com.example.fundraise.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String donorName;
    private int amount;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "intern_id")
    private Intern intern;
}
