package org.example.srg3springminiproject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

}
