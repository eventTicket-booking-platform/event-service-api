package com.ec7205.event_hub.event_service_api.mapper;

import com.ec7205.event_hub.event_service_api.dto.response.CategoryResponse;
import com.ec7205.event_hub.event_service_api.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.getIsActive())
                .build();
    }
}
