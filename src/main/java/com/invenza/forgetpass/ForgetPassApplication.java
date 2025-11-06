package com.invenza.forgetpass;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.invenza.forgetpass.model.User;
import com.invenza.forgetpass.repository.UserRepository;

@SpringBootApplication
public class ForgetPassApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForgetPassApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Optional: Insert a test user to verify DB connection
    @Bean
    public CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("test@example.com").isEmpty()) {
                User user = new User();
                user.setEmail("test@example.com");
                user.setPassword(passwordEncoder.encode("password123"));
                userRepository.save(user);
                System.out.println("Test user inserted successfully!");
            } else {
                System.out.println("Test user already exists.");
            }
        };
    }
}
