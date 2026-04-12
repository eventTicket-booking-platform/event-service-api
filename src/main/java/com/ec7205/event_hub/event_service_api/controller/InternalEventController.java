package com.ec7205.event_hub.event_service_api.controller;

import com.ec7205.event_hub.event_service_api.dto.response.BookingInfoResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventExistsResponse;
import com.ec7205.event_hub.event_service_api.dto.response.TicketTypeResponse;
import com.ec7205.event_hub.event_service_api.service.InternalEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event-service/api/v1/internal/events")
@RequiredArgsConstructor
public class InternalEventController {

    private final InternalEventService internalEventService;

    @GetMapping("/{eventId}/booking-info")
    public ResponseEntity<BookingInfoResponse> getBookingInfo(@PathVariable Long eventId) {
        return ResponseEntity.ok(internalEventService.getBookingInfo(eventId));
    }

    @GetMapping("/{eventId}/exists")
    public ResponseEntity<EventExistsResponse> eventExists(@PathVariable Long eventId) {
        return ResponseEntity.ok(internalEventService.eventExists(eventId));
    }

    @GetMapping("/{eventId}/ticket-types")
    public ResponseEntity<List<TicketTypeResponse>> getTicketTypes(@PathVariable Long eventId) {
        return ResponseEntity.ok(internalEventService.getEventTicketTypes(eventId));
    }
}
