package com.ec7205.event_hub.event_service_api.controller;

import com.ec7205.event_hub.event_service_api.dto.response.CategoryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventDetailResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.EventPaginateResponseDto;
import com.ec7205.event_hub.event_service_api.service.CategoryService;
import com.ec7205.event_hub.event_service_api.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event-service/api/v1/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;
    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<EventPaginateResponseDto> getPublicEvents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String city,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.getPublicEvents(search, category, city, pageable));
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<EventDetailResponse> getEventDetails(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getPublicEventDetails(eventId));
    }

    @GetMapping("/categories")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getActiveCategories());
    }
}
