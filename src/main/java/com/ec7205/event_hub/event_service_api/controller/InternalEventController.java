package com.ec7205.event_hub.event_service_api.controller;

import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketsRequest;
import com.ec7205.event_hub.event_service_api.dto.response.BookingInfoResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventExistsResponse;
import com.ec7205.event_hub.event_service_api.dto.response.TicketTypeResponse;
import com.ec7205.event_hub.event_service_api.service.InternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-service/api/v1/internal/events")
@RequiredArgsConstructor
public class InternalEventController {

    private final InternalEventService internalEventService;

    @PostMapping("/{eventId}/reserve")
    public ResponseEntity<Void> reserveTickets(
            @PathVariable Long eventId,
            @Valid @RequestBody ReserveTicketsRequest request
    ) {
        internalEventService.reserveTickets(eventId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}/booking-info")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BookingInfoResponse> getBookingInfo(@PathVariable Long eventId) {
        return ResponseEntity.ok(internalEventService.getBookingInfo(eventId));
    }

    @GetMapping("/{eventId}/exists")
    @PreAuthorize("permitAll()")
    public ResponseEntity<EventExistsResponse> eventExists(@PathVariable Long eventId) {
        return ResponseEntity.ok(internalEventService.eventExists(eventId));
    }

    @GetMapping("/{eventId}/ticket-types")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<TicketTypeResponse>> getTicketTypes(@PathVariable Long eventId) {
        return ResponseEntity.ok(internalEventService.getEventTicketTypes(eventId));
    }
}
