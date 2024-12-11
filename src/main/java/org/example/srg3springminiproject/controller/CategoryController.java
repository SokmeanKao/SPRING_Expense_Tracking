package org.example.srg3springminiproject.controller;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.srg3springminiproject.model.Category;
import org.example.srg3springminiproject.model.Expense;
import org.example.srg3springminiproject.model.response.APIResponse;
import org.example.srg3springminiproject.model.response.CategoryResponse;
import org.example.srg3springminiproject.model.response.RemoveResponse;
import org.example.srg3springminiproject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.srg3springminiproject.model.request.CategoryRequest;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping()
    public ResponseEntity<APIResponse<List<Category>>> findAllCategory(@RequestParam(defaultValue = "1") @Positive Integer offset ,
                                                                       @RequestParam(defaultValue = "5") @Positive Integer limit){
        APIResponse<List<Category>> response=APIResponse.<List<Category>> builder()
                .message("All Categories have been successfully founded")
                .payload(categoryService.findAllCategory(offset,limit))
                .status(HttpStatus.OK)
                .creationDate(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> findCategoryById(@PathVariable UUID id){
        APIResponse<CategoryResponse> response= APIResponse.<CategoryResponse> builder()
                .message("The category have been successfully founded")
                .payload(categoryService.findCategoryById(id))
                .status(HttpStatus.OK)
                .creationDate(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping()
    public Category insertCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.insertCategory(categoryRequest);
    }
    //update category
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest categoryRequest){
        CategoryResponse updateCategory=categoryService.updateCategory(id, categoryRequest);
        APIResponse<CategoryResponse> response = APIResponse.<CategoryResponse>builder()
                .message("Update  Category Success!")
                .payload(updateCategory)
                .status(HttpStatus.OK)
                .creationDate(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<RemoveResponse<Category>> removeCategory(@PathVariable UUID id){
        categoryService.removeCategory(id);
        RemoveResponse<Category> response = RemoveResponse.<Category>builder()
                .message("The category has been successfully deleted.")
                .status(HttpStatus.OK)
                .creationDate(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

