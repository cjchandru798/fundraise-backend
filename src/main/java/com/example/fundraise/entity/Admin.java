package com.example.fundraise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password; // hashed
    private String name;

    @Column(name = "super_admin")
    private boolean superAdmin = false;

    // ✅ Custom constructor (excluding id and superAdmin)
    public Admin(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        // superAdmin defaults to false unless set explicitly
    }
}
