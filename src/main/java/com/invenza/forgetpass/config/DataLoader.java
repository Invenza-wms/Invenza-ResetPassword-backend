package com.invenza.forgetpass.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.invenza.forgetpass.model.User;
import com.invenza.forgetpass.repository.UserRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByEmail("demo@gmail.com").isPresent()) {
            User user = new User();
            user.setEmail("demo@gmail.com");
            user.setMobile("9876543210");
            user.setPassword(encoder.encode("password123"));
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("✅ Demo user inserted.");
        }
    }
}
