package com.ec7205.event_hub.event_service_api.service.impl;

import com.ec7205.event_hub.event_service_api.dto.request.CreateCategoryRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateCategoryRequest;
import com.ec7205.event_hub.event_service_api.dto.response.ApiMessageResponse;
import com.ec7205.event_hub.event_service_api.dto.response.CategoryResponse;
import com.ec7205.event_hub.event_service_api.entity.Category;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.exception.ResourceNotFoundException;
import com.ec7205.event_hub.event_service_api.mapper.CategoryMapper;
import com.ec7205.event_hub.event_service_api.repository.CategoryRepository;
import com.ec7205.event_hub.event_service_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ApiMessageResponse createCategory(CreateCategoryRequest request) {
        String normalizedName = request.getName().trim();
        validateDuplicateCategoryName(normalizedName, null);

        Category category = Category.builder()
                .name(normalizedName)
                .description(trimToNull(request.getDescription()))
                .isActive(Boolean.TRUE)
                .build();

        Category savedCategory = categoryRepository.save(category);
        return ApiMessageResponse.builder()
                .message("Category created successfully")
                .resourceId(savedCategory.getId())
                .build();
    }

    @Override
    public ApiMessageResponse updateCategory(Long categoryId, UpdateCategoryRequest request) {
        Category category = getCategoryOrThrow(categoryId);
        String normalizedName = request.getName().trim();
        validateDuplicateCategoryName(normalizedName, categoryId);

        category.setName(normalizedName);
        category.setDescription(trimToNull(request.getDescription()));

        return ApiMessageResponse.builder()
                .message("Category updated successfully")
                .resourceId(category.getId())
                .build();
    }

    @Override
    public ApiMessageResponse deleteCategory(Long categoryId) {
        Category category = getCategoryOrThrow(categoryId);
        category.setIsActive(Boolean.FALSE);

        return ApiMessageResponse.builder()
                .message("Category deactivated successfully")
                .resourceId(category.getId())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getActiveCategories() {
        return categoryRepository.findAllByIsActiveTrueOrderByNameAsc().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    private Category getCategoryOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for id: " + categoryId));
    }

    private void validateDuplicateCategoryName(String name, Long excludedCategoryId) {
        boolean duplicateExists = excludedCategoryId == null
                ? categoryRepository.existsByNameIgnoreCaseAndIsActiveTrue(name)
                : categoryRepository.existsByNameIgnoreCaseAndIsActiveTrueAndIdNot(name, excludedCategoryId);

        if (duplicateExists) {
            throw new ConflictException("An active category with the given name already exists");
        }
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmedValue = value.trim();
        return trimmedValue.isEmpty() ? null : trimmedValue;
    }
}
