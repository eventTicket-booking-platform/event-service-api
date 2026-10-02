package com.ec7205.event_hub.event_service_api.service.impl;

import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketRequest;
import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketsRequest;
import com.ec7205.event_hub.event_service_api.dto.response.BookingInfoResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventExistsResponse;
import com.ec7205.event_hub.event_service_api.dto.response.TicketTypeResponse;
import com.ec7205.event_hub.event_service_api.entity.Event;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.exception.ResourceNotFoundException;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.repository.EventRepository;
import com.ec7205.event_hub.event_service_api.repository.TicketTypeRepository;
import com.ec7205.event_hub.event_service_api.service.InternalEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalEventServiceImpl implements InternalEventService {

    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final EventMapper eventMapper;

    @Override
    public BookingInfoResponse getBookingInfo(Long eventId) {
        Event event = getDetailedEventOrThrow(eventId);
        return eventMapper.toBookingInfoResponse(event);
    }

    @Override
    public EventExistsResponse eventExists(Long eventId) {
        return EventExistsResponse.builder()
                .exists(eventRepository.existsById(eventId))
                .build();
    }

    @Override
    public List<TicketTypeResponse> getEventTicketTypes(Long eventId) {
        Event event = getDetailedEventOrThrow(eventId);
        return event.getTicketTypes().stream()
                .map(eventMapper::toTicketTypeResponse)
                .toList();
    }

    @Override
    @Transactional
    public void reserveTickets(Long eventId, ReserveTicketsRequest request) {

        getDetailedEventOrThrow(eventId);

        for (ReserveTicketRequest ticket : request.getTickets()) {

            int updatedRows = ticketTypeRepository.reserveTickets(
                    ticket.getTicketTypeId(),
                    ticket.getQuantity()
            );

            if (updatedRows == 0) {
                throw new ConflictException(
                        "Not enough tickets available for ticket type: "
                                + ticket.getTicketTypeId()
                );
            }
        }
    }

    @Override
    @Transactional
    public void releaseTickets(Long eventId, ReserveTicketsRequest request) {

        getDetailedEventOrThrow(eventId);

        for (ReserveTicketRequest ticket : request.getTickets()) {

            int updatedRows = ticketTypeRepository.releaseTickets(
                    ticket.getTicketTypeId(),
                    ticket.getQuantity()
            );

            if (updatedRows == 0) {
                throw new ConflictException(
                        "Unable to release tickets for ticket type: "
                                + ticket.getTicketTypeId()
                );
            }
        }
    }
    private Event getDetailedEventOrThrow(Long eventId) {
        return eventRepository.findDetailedById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for id: " + eventId));
    }
}
