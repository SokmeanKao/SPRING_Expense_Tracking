package org.example.srg3springminiproject.service.serviceImpl;
import lombok.AllArgsConstructor;
import org.example.srg3springminiproject.exception.NotFoundException;
import org.example.srg3springminiproject.model.Category;
import org.example.srg3springminiproject.model.request.CategoryRequest;
import org.example.srg3springminiproject.model.response.CategoryResponse;
import org.example.srg3springminiproject.repository.CategoryRepository;
import org.example.srg3springminiproject.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final UserServiceImpl userServiceImpl;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> findAllCategory(Integer offset, Integer limit) {
        UUID userId = userServiceImpl.getUsernameOfCurrentUser();
        offset = (offset - 1) * limit;
        return categoryRepository.findAllCategory(offset, limit,userId);
    }

    @Override
    public CategoryResponse findCategoryById(UUID id) {
        UUID UserId = userServiceImpl.getUsernameOfCurrentUser();
        Category category = categoryRepository.findCategoryById(id,UserId);
        if (category == null) {
            throw new NotFoundException("The category with id " + id + " doesn't exist.");
        }
        else {
            return modelMapper.map(category, CategoryResponse.class);
        }
    }

    @Override
    public Category insertCategory(CategoryRequest categoryRequest) {
        UUID UserId = userServiceImpl.getUsernameOfCurrentUser();
        System.out.println(UserId);
        Category categoryId = categoryRepository.insertCategory(categoryRequest,UserId);
        return categoryId;
    }

    @Override
    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.updateCategory(id, categoryRequest);
        if (category == null) {
            throw new NotFoundException("The category with id " + id + " doesn't exist.");
        }
        else {
            return modelMapper.map(category, CategoryResponse.class);
        }
    }

    @Override
    public Boolean removeCategory(UUID id) {
        boolean removed = categoryRepository.removeCategory(id);
        if (!removed) {
            throw new NotFoundException("The category with id " + id + " doesn't exist.");
        }
        return removed;
    }
}