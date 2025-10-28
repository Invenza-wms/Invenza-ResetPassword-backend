package com.invenza.forgetpass.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "otp_tokens")
public class OtpToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String resetToken;
    private Instant expiresAt;
    private boolean used = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public OtpToken() {}

    // Getters & Setters
    public Long getId() { return id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }

    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
