package org.example.srg3springminiproject.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @NotBlank
    @Email(message = "Invalid Email Input")
    private String email;
    @NotNull
    @NotBlank
    private String password;
}
