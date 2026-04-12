package com.ec7205.event_hub.event_service_api.service;

import com.ec7205.event_hub.event_service_api.dto.request.CreateCategoryRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateCategoryRequest;
import com.ec7205.event_hub.event_service_api.dto.response.ApiMessageResponse;
import com.ec7205.event_hub.event_service_api.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    ApiMessageResponse createCategory(CreateCategoryRequest request);

    ApiMessageResponse updateCategory(Long categoryId, UpdateCategoryRequest request);

    ApiMessageResponse deleteCategory(Long categoryId);

    List<CategoryResponse> getActiveCategories();
}
