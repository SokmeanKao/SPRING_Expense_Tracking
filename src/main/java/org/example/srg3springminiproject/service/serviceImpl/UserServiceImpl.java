package org.example.srg3springminiproject.service.serviceImpl;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.example.srg3springminiproject.config.PasswordConfig;
import org.example.srg3springminiproject.exception.InvalidInputException;
import org.example.srg3springminiproject.exception.NotFoundException;
import org.example.srg3springminiproject.jwt.JwtService;
import org.example.srg3springminiproject.model.Otp;
import org.example.srg3springminiproject.model.User;
import org.example.srg3springminiproject.model.request.ForgetRequest;
import org.example.srg3springminiproject.model.request.LoginRequest;
import org.example.srg3springminiproject.model.request.RegisterRequest;
import org.example.srg3springminiproject.model.response.AuthResponse;
import org.example.srg3springminiproject.model.response.UserResponse;
import org.example.srg3springminiproject.repository.OtpRepository;
import org.example.srg3springminiproject.repository.UserRepository;
import org.example.srg3springminiproject.service.AuthService;
import org.example.srg3springminiproject.service.UserService;
import org.example.srg3springminiproject.util.EmailUtil;
import org.example.srg3springminiproject.util.OtpUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final AuthService authService;
    private final PasswordConfig passwordConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public UserResponse register(RegisterRequest registerRequest) throws MessagingException {
        User checkEmail = userRepository.getUserByEmail(registerRequest.getEmail());
        if(checkEmail != null) {
            throw new InvalidInputException("Email already register, Please enter another email");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword()) || registerRequest.getPassword().length() < 8 ) {
            throw new InvalidInputException("Passwords do not match or have at least 8 characters");
        } else {
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        }

        String otpCode = otpUtil.generateOtp();
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setProfileImage(registerRequest.getProfileImage());
        User savedUser = userRepository.save(user);
        Otp otp = new Otp();
        otp.setUserId(savedUser.getUserId());
        otp.setOtpCode(otpCode);
        otp.setIssuedAt(new Timestamp(System.currentTimeMillis()));
        otp.setExpirationTime(calculateExpirationTime());
        otpRepository.insertOtp(otp);
        emailUtil.sendOtpEmail(savedUser.getEmail(), otpCode);
        return new UserResponse(savedUser.getUserId(), savedUser.getEmail(), savedUser.getProfileImage());
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = authService.loadUserByUsername(loginRequest.getEmail());
        if (userDetails != null) {
            User user = userRepository.getUserByEmail(loginRequest.getEmail());
            if (user != null) {
                Otp latestOtp = otpRepository.getOtpByUserId(user.getUserId());
                if (latestOtp == null || !latestOtp.isVerified()) throw new NotFoundException("Your account is not verified yet, please try again.");

                if (!passwordConfig.passwordEncoder().matches(loginRequest.getPassword(), userDetails.getPassword())) throw new NotFoundException("Passwords do not match, please enter correct password");

                String token = jwtService.generateToken(userDetails.getUsername());
                return new AuthResponse(token);
            }
        }throw new NotFoundException("User not found with email " + loginRequest.getEmail());

    }

    @Override
    public boolean verifyOtp(String otpCode) {
        System.out.println("OTP code is: " + otpCode);
        Otp latestOtp = otpRepository.getLatestOtpByCode(otpCode);
        if (latestOtp != null && !latestOtp.isVerified()) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            Timestamp expirationTime = latestOtp.getExpirationTime();
            if (expirationTime.after(currentTime)) {
                if (latestOtp.getOtpCode().equals(otpCode)) {
                    if (!latestOtp.isVerified()) {
                        latestOtp.setVerified(true);
                        otpRepository.updateOtp(latestOtp);
                    }
                    return true;
                }

                throw new NotFoundException("OTP code is Invalid or Expiration, please try again.");
            }
        }

        throw new NotFoundException("Your account is already verified");
    }

    @Override
    public String resendOtp(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) throw new NotFoundException("user with email " + email + " not found.");
        String newOtpCode = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, newOtpCode);
        } catch (MessagingException e) {
            return "Failed to send OTP email. Please try again later.";
        }
        Otp existingOtp = otpRepository.getLatestUnverifiedOtpByEmail(email);
        if (existingOtp == null) throw new NotFoundException("Failed to Resend OTP, Your Account are already Verify");
        existingOtp.setOtpCode(newOtpCode);
        existingOtp.setIssuedAt(new Timestamp(System.currentTimeMillis()));
        existingOtp.setExpirationTime(calculateExpirationTime());
        System.out.println(existingOtp);
        otpRepository.updateOtp(existingOtp);
        return "OTP resent successfully.";
    }

    @Override
    public UserResponse forgetPassword(ForgetRequest forgetRequest, String email) {
        // Check if the email exists in the database
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("Email not found for update password");
        }
        // Check if the email is verified using OTP
        Otp latestOtp = otpRepository.getOtpByUserId(user.getUserId());
        if (latestOtp == null || !latestOtp.isVerified()) {
            throw new InvalidInputException("This Email is not verified for updating the password, please verify your email first.");
        }
        // Validate the new password and confirm password
        if (!forgetRequest.getPassword().equals(forgetRequest.getConfirmPassword()) || forgetRequest.getPassword().length() < 8) {
            throw new InvalidInputException("Passwords do not match or password length is less than 8 characters");
        }

        // Update the user's password
        forgetRequest.setPassword(passwordEncoder.encode(forgetRequest.getPassword()));
        User userPassword = userRepository.updatePassword(forgetRequest,email);
        return modelMapper.map(userPassword, UserResponse.class);
    }

    private Timestamp calculateExpirationTime() {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + (2 * 60 * 1000);
        return new Timestamp(expirationTimeMillis);
    }
    @Override
    public UUID getUsernameOfCurrentUser() {
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            UUID userId = userDetails.getUserId();
            System.out.println(userId);
            return userId;
    }
}
