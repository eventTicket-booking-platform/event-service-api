package com.ec7205.event_hub.event_service_api.service;

import com.ec7205.event_hub.event_service_api.dto.request.CreateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventStatusRequest;
import com.ec7205.event_hub.event_service_api.dto.response.AdminEventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.ApiMessageResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventDetailResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.AdminEventPaginateResponseDto;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.EventPaginateResponseDto;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.springframework.data.domain.Pageable;

public interface EventService {

    ApiMessageResponse createEvent(CreateEventRequest request);

    ApiMessageResponse updateEvent(Long eventId, UpdateEventRequest request);

    ApiMessageResponse changeEventStatus(Long eventId, UpdateEventStatusRequest request);

    ApiMessageResponse deleteEvent(Long eventId);

    EventPaginateResponseDto getPublicEvents(String search, Long categoryId, String city, Pageable pageable);

    EventDetailResponse getPublicEventDetails(Long eventId);

    AdminEventPaginateResponseDto getAdminEvents(String search, EventStatus status, Pageable pageable);
}
