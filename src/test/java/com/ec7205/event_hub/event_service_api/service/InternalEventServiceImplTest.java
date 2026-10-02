package com.ec7205.event_hub.event_service_api.service;

import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketRequest;
import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketsRequest;
import com.ec7205.event_hub.event_service_api.entity.Event;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.repository.EventRepository;
import com.ec7205.event_hub.event_service_api.repository.TicketTypeRepository;
import com.ec7205.event_hub.event_service_api.service.impl.InternalEventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InternalEventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketTypeRepository ticketTypeRepository;

    private InternalEventServiceImpl internalEventService;

    @BeforeEach
    void setUp() {
        internalEventService = new InternalEventServiceImpl(
                eventRepository,
                ticketTypeRepository,
                null
        );
    }

    @Test
    void shouldReserveTicketsWhenEnoughQuantityAvailable() {

        Long eventId = 1L;
        Long ticketTypeId = 10L;
        int quantity = 3;

        Event event = new Event();

        ReserveTicketRequest ticketRequest =
                ReserveTicketRequest.builder()
                        .ticketTypeId(ticketTypeId)
                        .quantity(quantity)
                        .build();

        ReserveTicketsRequest request =
                ReserveTicketsRequest.builder()
                        .tickets(List.of(ticketRequest))
                        .build();

        when(eventRepository.findDetailedById(eventId))
                .thenReturn(Optional.of(event));

        when(ticketTypeRepository.reserveTickets(ticketTypeId, quantity))
                .thenReturn(1);

        internalEventService.reserveTickets(eventId, request);

        verify(ticketTypeRepository)
                .reserveTickets(ticketTypeId, quantity);
    }

    @Test
    void shouldThrowConflictWhenNotEnoughTicketsAvailable() {

        Long eventId = 1L;
        Long ticketTypeId = 10L;
        int quantity = 8;

        Event event = new Event();

        ReserveTicketRequest ticketRequest =
                ReserveTicketRequest.builder()
                        .ticketTypeId(ticketTypeId)
                        .quantity(quantity)
                        .build();

        ReserveTicketsRequest request =
                ReserveTicketsRequest.builder()
                        .tickets(List.of(ticketRequest))
                        .build();

        when(eventRepository.findDetailedById(eventId))
                .thenReturn(Optional.of(event));

        when(ticketTypeRepository.reserveTickets(ticketTypeId, quantity))
                .thenReturn(0);

        assertThrows(
                ConflictException.class,
                () -> internalEventService.reserveTickets(eventId, request)
        );
    }
}

