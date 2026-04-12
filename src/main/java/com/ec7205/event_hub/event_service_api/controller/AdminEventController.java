package com.ec7205.event_hub.event_service_api.controller;

import com.ec7205.event_hub.event_service_api.dto.request.CreateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventStatusRequest;
import com.ec7205.event_hub.event_service_api.dto.response.AdminEventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.ApiMessageResponse;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.AdminEventPaginateResponseDto;
import com.ec7205.event_hub.event_service_api.service.EventService;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event-service/api/v1/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<ApiMessageResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiMessageResponse> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventRequest request
    ) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, request));
    }

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<ApiMessageResponse> updateEventStatus(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventStatusRequest request
    ) {
        return ResponseEntity.ok(eventService.changeEventStatus(eventId, request));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiMessageResponse> deleteEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.deleteEvent(eventId));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<AdminEventPaginateResponseDto> getAdminEvents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) EventStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.getAdminEvents(search, status, pageable));
    }
}
