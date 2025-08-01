package com.example.fundraise.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "created_at")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "intern_id")
    @JsonIgnore
    private Intern intern;

    @PrePersist
    public void setDefaultDate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }

}

