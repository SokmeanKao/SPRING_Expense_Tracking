package org.example.srg3springminiproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.srg3springminiproject.model.response.UserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    private UUID expenseId;
    private int amount;
    private String description;
    private LocalDateTime date;
    private Category categories;
    private UserResponse user;
}
