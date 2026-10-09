package com.ec7205.event_hub.event_service_api.integration;

import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketRequest;
import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketsRequest;
import com.ec7205.event_hub.event_service_api.entity.*;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.repository.*;
import com.ec7205.event_hub.event_service_api.service.InternalEventService;
import com.ec7205.event_hub.event_service_api.service.impl.InternalEventServiceImpl;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(InternalEventServiceImpl.class)
class TicketReservationMySqlIT extends MySqlIntegrationTest {
    @Autowired private InternalEventService service;
    @Autowired private TicketTypeRepository tickets;
    @Autowired private EventRepository events;
    @Autowired private CategoryRepository categories;
    // Reservation does not use the response mapper; all persistence and transactions are real.
    @MockBean private EventMapper mapper;

    @Test
    void twentyConcurrentReservationsCommitExactlyTenWithoutNegativeStock() throws Exception {
        Category category = categories.saveAndFlush(Category.builder()
                .name("Concurrency").isActive(true).build());
        Event event = events.saveAndFlush(Event.builder()
                .title("Concurrent reservations").description("MySQL stock invariant")
                .category(category)
                .venue(Venue.builder().name("Venue").city("Colombo").address("Address").build())
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(2))
                .status(EventStatus.PUBLISHED).createdBy("host").build());
        TicketType ticket = tickets.saveAndFlush(TicketType.builder()
                .name("Standard").price(BigDecimal.TEN).totalQuantity(10).availableQuantity(10)
                .event(event).build());
        Long eventId = event.getEvent_id();
        Long ticketId = ticket.getId();

        List<Boolean> results = ConcurrentAttempts.run(20, index -> {
            // Each invocation runs in its own service transaction and database connection.
            boolean reserved;
            try {
                service.reserveTickets(eventId, ReserveTicketsRequest.builder()
                        .tickets(List.of(new ReserveTicketRequest(ticketId, 1))).build());
                reserved = true;
            } catch (ConflictException soldOut) {
                reserved = false;
            }
            assertTrue(tickets.findById(ticketId).orElseThrow().getAvailableQuantity() >= 0);
            return reserved;
        });

        assertEquals(10L, results.stream().filter(Boolean::booleanValue).count());
        assertEquals(10L, results.stream().filter(result -> !result).count());
        assertEquals(0, tickets.findById(ticketId).orElseThrow().getAvailableQuantity());
        assertThrows(ConflictException.class, () -> service.reserveTickets(eventId,
                ReserveTicketsRequest.builder()
                        .tickets(List.of(new ReserveTicketRequest(ticketId, 1))).build()));
        assertEquals(0, tickets.findById(ticketId).orElseThrow().getAvailableQuantity());
    }
}
