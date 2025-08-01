package com.example.fundraise;

import com.example.fundraise.entity.Admin;
import com.example.fundraise.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FundraiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundraiseApplication.class, args);
	}

	// ✅ Automatically create admin user on app start if not already in DB
	@Bean
	CommandLineRunner createAdmin(AdminRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByEmail("admin@org.com").isEmpty()) {
				Admin admin = new Admin(null, "admin@org.com", encoder.encode("admin123"), "Admin User");
				repo.save(admin);
				System.out.println("✅ Admin user created: admin@org.com / admin123");
			} else {
				System.out.println("ℹ️ Admin user already exists.");
			}
		};
	}
}
