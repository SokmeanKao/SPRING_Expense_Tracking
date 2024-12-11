package org.example.srg3springminiproject.service;

import jakarta.mail.MessagingException;
import org.example.srg3springminiproject.model.User;
import org.example.srg3springminiproject.model.request.ForgetRequest;
import org.example.srg3springminiproject.model.request.LoginRequest;
import org.example.srg3springminiproject.model.request.RegisterRequest;
import org.example.srg3springminiproject.model.response.AuthResponse;
import org.example.srg3springminiproject.model.response.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest)throws MessagingException;

    AuthResponse login(LoginRequest loginRequest);

    boolean verifyOtp(String otpCode);

    String resendOtp(String email);

    UserResponse forgetPassword(ForgetRequest forgetRequest, String email);

    UUID getUsernameOfCurrentUser();

}
