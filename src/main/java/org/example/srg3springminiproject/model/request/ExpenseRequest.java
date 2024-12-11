package org.example.srg3springminiproject.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    @NotNull
    @Positive
    private int amount;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotNull
    private LocalDateTime date;
    @NotNull
    private UUID categoryId;
}
