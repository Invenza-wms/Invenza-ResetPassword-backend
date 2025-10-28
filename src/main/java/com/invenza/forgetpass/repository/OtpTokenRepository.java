package com.invenza.forgetpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invenza.forgetpass.model.OtpToken;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByCode(String code);
    Optional<OtpToken> findByResetToken(String resetToken);
}

