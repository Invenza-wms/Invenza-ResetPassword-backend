package com.invenza.forgetpass.dto.requests;

import jakarta.validation.constraints.NotBlank;

public class SendOtp {

    @NotBlank
    private String identifier; // Email or Mobile

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}

