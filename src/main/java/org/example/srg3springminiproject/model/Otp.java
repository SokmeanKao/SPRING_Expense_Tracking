package org.example.srg3springminiproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    private UUID id;
    private UUID userId;
    private String otpCode;
    private Timestamp issuedAt;
    private Timestamp expirationTime;
    private boolean verified;
}
