package com.invenza.forgetpass.dto.requests;

import jakarta.validation.constraints.NotBlank;

public class VerifyOtp {

    @NotBlank
    private String identifier;

    @NotBlank
    private String otp;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
