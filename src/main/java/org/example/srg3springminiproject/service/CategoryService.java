package org.example.srg3springminiproject.service;
import org.example.srg3springminiproject.model.Category;
import org.example.srg3springminiproject.model.request.CategoryRequest;

import org.example.srg3springminiproject.model.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> findAllCategory(Integer offset, Integer limit);

    CategoryResponse findCategoryById(UUID id);

    Category insertCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest);

    Boolean removeCategory(UUID id);
}
