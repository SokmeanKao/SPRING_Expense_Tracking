package org.example.srg3springminiproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private UUID userId;
    private String email;
    private String profileImage;
}
