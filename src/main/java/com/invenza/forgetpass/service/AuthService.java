package com.invenza.forgetpass.service;

import com.invenza.forgetpass.dto.requests.SendOtp;
import com.invenza.forgetpass.dto.requests.VerifyOtp;
import com.invenza.forgetpass.dto.requests.ResetPasswordRequest;
import com.invenza.forgetpass.model.OtpToken;
import com.invenza.forgetpass.model.User;
import com.invenza.forgetpass.repository.OtpTokenRepository;
import com.invenza.forgetpass.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpTokenRepository otpTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, OtpTokenRepository otpTokenRepository) {
        this.userRepository = userRepository;
        this.otpTokenRepository = otpTokenRepository;
    }

    // ✅ Send OTP (dummy SMS)
    public void sendOtp(SendOtp req) {
        String identifier = req.getIdentifier();

        User user;
        if (identifier.contains("@")) {
            user = userRepository.findByEmail(identifier).orElse(null);
        } else {
            user = userRepository.findByMobile(identifier).orElse(null);
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        }

        String otp = String.format("%06d", new Random().nextInt(1000000));
        System.out.println("🔹 OTP sent (dummy SMS): " + otp);

        OtpToken token = new OtpToken();
        token.setUser(user);
        token.setCode(otp);
        token.setExpiresAt(Instant.now().plusSeconds(300)); 
        token.setUsed(false);

        otpTokenRepository.save(token);
    }

    // ✅ OTP Verification
    public String verifyOtp(VerifyOtp req) {
        Optional<OtpToken> otpData = otpTokenRepository.findByCode(req.getOtp());

        if (otpData.isEmpty()) {
            throw new IllegalArgumentException("Invalid OTP!");
        }

        OtpToken token = otpData.get();
        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("OTP expired!");
        }

        String resetToken = UUID.randomUUID().toString();

        token.setResetToken(resetToken);
        otpTokenRepository.save(token);

        return resetToken;
    }

    // ✅ Reset Password
    public void resetPassword(ResetPasswordRequest req) {
        Optional<OtpToken> otpData = otpTokenRepository.findByResetToken(req.getResetToken());

        if (otpData.isEmpty()) {
            throw new IllegalArgumentException("Invalid reset token!");
        }

        OtpToken token = otpData.get();
        User user = token.getUser();

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        token.setUsed(true);
        otpTokenRepository.save(token);

        System.out.println("✅ Password updated successfully!");
    }
}
