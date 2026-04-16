package com.ec7205.event_hub.event_service_api.controller;

import com.ec7205.event_hub.event_service_api.exception.BadRequestException;
import com.ec7205.event_hub.event_service_api.dto.request.CreateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventStatusRequest;
import com.ec7205.event_hub.event_service_api.dto.response.ApiMessageResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventDetailResponse;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.AdminEventPaginateResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import com.ec7205.event_hub.event_service_api.service.EventService;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/event-service/api/v1/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','host')")
    public ResponseEntity<ApiMessageResponse> createEvent(
            @RequestPart("request") String requestJson,
            @RequestPart(value = "bannerimg", required = false) MultipartFile bannerimg
    ) {
        CreateEventRequest request = parseAndValidate(requestJson, CreateEventRequest.class);
        return ResponseEntity.ok(eventService.createEvent(request, bannerimg));
    }

    @PutMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','host')")
    public ResponseEntity<ApiMessageResponse> updateEvent(
            @PathVariable Long eventId,
            @RequestPart("request") String requestJson,
            @RequestPart(value = "bannerimg", required = false) MultipartFile bannerimg
    ) {
        UpdateEventRequest request = parseAndValidate(requestJson, UpdateEventRequest.class);
        return ResponseEntity.ok(eventService.updateEvent(eventId, request, bannerimg));
    }

    @PatchMapping("/{eventId}/status")
    @PreAuthorize("hasAnyAuthority('admin','host')")
    public ResponseEntity<ApiMessageResponse> updateEventStatus(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventStatusRequest request
    ) {
        return ResponseEntity.ok(eventService.changeEventStatus(eventId, request));
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('admin','host')")
    public ResponseEntity<ApiMessageResponse> deleteEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.deleteEvent(eventId));
    }

    @GetMapping("/admin/{eventId}")
    @PreAuthorize("hasAnyAuthority('admin','host')")
    public ResponseEntity<EventDetailResponse> getAdminEventDetails(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getAdminEventDetails(eventId));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasAnyAuthority('admin','host')")
    public ResponseEntity<AdminEventPaginateResponseDto> getAdminEvents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) EventStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.getAdminEvents(search, status, pageable));
    }

    private <T> T parseAndValidate(String requestJson, Class<T> requestType) {
        try {
            T request = objectMapper.readValue(requestJson, requestType);
            var violations = validator.validate(request);
            if (!violations.isEmpty()) {
                ConstraintViolation<T> violation = violations.iterator().next();
                throw new BadRequestException(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            return request;
        } catch (BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestException("Invalid request payload");
        }
    }
}
