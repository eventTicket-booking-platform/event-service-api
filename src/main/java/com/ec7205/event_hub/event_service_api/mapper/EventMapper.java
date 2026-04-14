package com.ec7205.event_hub.event_service_api.mapper;

import com.ec7205.event_hub.event_service_api.dto.request.TicketTypeRequest;
import com.ec7205.event_hub.event_service_api.dto.request.VenueRequest;
import com.ec7205.event_hub.event_service_api.dto.response.AdminEventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.BookingInfoResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventDetailResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.TicketTypeResponse;
import com.ec7205.event_hub.event_service_api.dto.response.VenueResponse;
import com.ec7205.event_hub.event_service_api.entity.Event;
import com.ec7205.event_hub.event_service_api.entity.TicketType;
import com.ec7205.event_hub.event_service_api.entity.Venue;
import com.ec7205.event_hub.event_service_api.utils.FileDataExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final FileDataExtractor fileDataExtractor;

    public Venue toVenueEntity(VenueRequest request) {
        return Venue.builder()
                .name(request.getName().trim())
                .city(request.getCity().trim())
                .address(request.getAddress().trim())
                .build();
    }

    public void updateVenue(Venue venue, VenueRequest request) {
        venue.setName(request.getName().trim());
        venue.setCity(request.getCity().trim());
        venue.setAddress(request.getAddress().trim());
    }

    public List<TicketType> toTicketTypeEntities(List<TicketTypeRequest> ticketTypeRequests, Event event) {
        return ticketTypeRequests.stream()
                .map(ticketTypeRequest -> TicketType.builder()
                        .name(ticketTypeRequest.getName().trim())
                        .price(ticketTypeRequest.getPrice())
                        .totalQuantity(ticketTypeRequest.getTotalQuantity())
                        .event(event)
                        .build())
                .toList();
    }

    public EventSummaryResponse toEventSummaryResponse(Event event) {
        return EventSummaryResponse.builder()
                .eventId(event.getEvent_id())
                .title(event.getTitle())
                .categoryName(event.getCategory().getName())
                .city(event.getVenue().getCity())
                .bannerUrl(resolveBannerUrl(event))
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .build();
    }

    public AdminEventSummaryResponse toAdminEventSummaryResponse(Event event) {
        return AdminEventSummaryResponse.builder()
                .eventId(event.getEvent_id())
                .title(event.getTitle())
                .categoryName(event.getCategory().getName())
                .city(event.getVenue().getCity())
                .bannerUrl(resolveBannerUrl(event))
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .status(event.getStatus())
                .build();
    }

    public EventDetailResponse toEventDetailResponse(Event event) {
        return EventDetailResponse.builder()
                .eventId(event.getEvent_id())
                .title(event.getTitle())
                .description(event.getDescription())
                .category(categoryMapper.toResponse(event.getCategory()))
                .venue(toVenueResponse(event.getVenue()))
                .bannerUrl(resolveBannerUrl(event))
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .status(event.getStatus())
                .ticketTypes(event.getTicketTypes().stream().map(this::toTicketTypeResponse).toList())
                .build();
    }

    public BookingInfoResponse toBookingInfoResponse(Event event) {
        return BookingInfoResponse.builder()
                .eventId(event.getEvent_id())
                .title(event.getTitle())
                .status(event.getStatus())
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .ticketTypes(event.getTicketTypes().stream().map(this::toTicketTypeResponse).toList())
                .build();
    }

    public VenueResponse toVenueResponse(Venue venue) {
        return VenueResponse.builder()
                .venueId(venue.getId())
                .name(venue.getName())
                .city(venue.getCity())
                .address(venue.getAddress())
                .build();
    }

    public TicketTypeResponse toTicketTypeResponse(TicketType ticketType) {
        return TicketTypeResponse.builder()
                .ticketTypeId(ticketType.getId())
                .name(ticketType.getName())
                .price(ticketType.getPrice())
                .totalQuantity(ticketType.getTotalQuantity())
                .build();
    }

    private String resolveBannerUrl(Event event) {
        if (event.getEventBanner() == null) {
            return null;
        }
        return fileDataExtractor.byteArrayToString(event.getEventBanner().getResourceUrl());
    }
}
