package com.invenza.forgetpass.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invenza.forgetpass.dto.requests.ForgotPasswordRequest;
import com.invenza.forgetpass.dto.requests.ResetPasswordRequest;
import com.invenza.forgetpass.dto.requests.SendOtp;
import com.invenza.forgetpass.dto.requests.VerifyOtp;
import com.invenza.forgetpass.dto.responses.ApiResponse;
import com.invenza.forgetpass.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody @Valid SendOtp req) {
        authService.sendOtp(req);
        return ResponseEntity.ok(new ApiResponse(true, "OTP sent successfully!", null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid VerifyOtp req) {
        String resetToken = authService.verifyOtp(req);
        return ResponseEntity.ok(new ApiResponse(true, "OTP verified successfully!", resetToken));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest req) {
        SendOtp otpRequest = new SendOtp();
        otpRequest.setIdentifier(req.getIdentifier());
        authService.sendOtp(otpRequest);

        return ResponseEntity.ok(new ApiResponse(true, "OTP sent successfully!", null));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest req) {
        authService.resetPassword(req);
        return ResponseEntity.ok(new ApiResponse(true, "Password reset successful!", null));
    }
}
