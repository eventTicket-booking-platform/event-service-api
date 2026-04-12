package com.ec7205.event_hub.event_service_api.service;

import com.ec7205.event_hub.event_service_api.dto.response.BookingInfoResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventExistsResponse;
import com.ec7205.event_hub.event_service_api.dto.response.TicketTypeResponse;

import java.util.List;

public interface InternalEventService {

    BookingInfoResponse getBookingInfo(Long eventId);

    EventExistsResponse eventExists(Long eventId);

    List<TicketTypeResponse> getEventTicketTypes(Long eventId);
}
