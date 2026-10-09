package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketRequest;
import com.ec7205.event_hub.event_service_api.dto.request.ReserveTicketsRequest;
import com.ec7205.event_hub.event_service_api.entity.*;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.service.impl.InternalEventServiceImpl;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(InternalEventServiceImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class TicketReleaseTest {
    @Autowired InternalEventServiceImpl service;
    @Autowired EventRepository events;
    @Autowired TicketTypeRepository tickets;
    @Autowired CategoryRepository categories;
    @Autowired VenueRepository venues;
    @Autowired TicketReleaseRepository releases;
    @Autowired PlatformTransactionManager transactionManager;
    @MockBean EventMapper mapper;

    @Test
    void repeatReleaseAfterLostResponseDoesNotRestoreInventoryAgain() {
        TicketType ticket = ticket();
        ReserveTicketsRequest request = request(ticket, 2);
        service.releaseTickets(ticket.getEvent().getEvent_id(), request);
        assertEquals(7, available(ticket));
        // Another booking consumes inventory before the first caller retries.
        tickets.reserveTickets(ticket.getId(), 1);
        service.releaseTickets(ticket.getEvent().getEvent_id(), request);
        assertEquals(6, available(ticket));
        assertTrue(releases.existsById(request.getReleaseId()));
    }

    @Test
    void failedMultiTicketReleaseRollsBackInventoryAndReceipt() {
        TicketType ticket = ticket();
        TicketType second = tickets.saveAndFlush(TicketType.builder().event(ticket.getEvent())
                .name("VIP").price(BigDecimal.TEN).totalQuantity(10).availableQuantity(5).build());
        ReserveTicketsRequest request = request(ticket, 2);
        request.setTickets(List.of(request.getTickets().get(0),
                ReserveTicketRequest.builder().ticketTypeId(second.getId()).quantity(6).build()));

        assertThrows(ConflictException.class,
                () -> service.releaseTickets(ticket.getEvent().getEvent_id(), request));
        assertEquals(5, available(ticket));
        assertEquals(5, available(second));
        assertFalse(releases.existsById(request.getReleaseId()));
        request.setTickets(List.of(request.getTickets().get(0),
                ReserveTicketRequest.builder().ticketTypeId(second.getId()).quantity(2).build()));
        service.releaseTickets(ticket.getEvent().getEvent_id(), request);
        assertEquals(7, available(ticket));
        assertEquals(7, available(second));
    }

    @Test
    void reusedReleaseIdWithDifferentQuantitiesIsRejected() {
        TicketType ticket = ticket();
        ReserveTicketsRequest request = request(ticket, 2);
        service.releaseTickets(ticket.getEvent().getEvent_id(), request);
        request.getTickets().get(0).setQuantity(1);
        assertThrows(ConflictException.class,
                () -> service.releaseTickets(ticket.getEvent().getEvent_id(), request));
        assertEquals(7, available(ticket));
    }

    @Test
    void concurrentReleaseRequestsRestoreInventoryOnlyOnce() throws Exception {
        TicketType ticket = ticket();
        ReserveTicketsRequest request = request(ticket, 2);
        Long eventId = ticket.getEvent().getEvent_id();
        ExecutorService workers = Executors.newFixedThreadPool(2);
        CountDownLatch start = new CountDownLatch(1);
        Callable<Void> task = () -> {
            assertTrue(start.await(5, TimeUnit.SECONDS));
            service.releaseTickets(eventId, request);
            return null;
        };
        try {
            Future<Void> first = workers.submit(task);
            Future<Void> second = workers.submit(task);
            start.countDown();
            first.get(10, TimeUnit.SECONDS);
            second.get(10, TimeUnit.SECONDS);
            assertEquals(7, available(ticket));
        } finally {
            workers.shutdownNow();
        }
    }

    @Test
    void existingCallersCanStillReleaseWithoutId() {
        TicketType ticket = ticket();
        ReserveTicketsRequest request = request(ticket, 2);
        request.setReleaseId(null);
        service.releaseTickets(ticket.getEvent().getEvent_id(), request);
        assertEquals(7, available(ticket));
    }

    private int available(TicketType ticket) {
        return tickets.findById(ticket.getId()).orElseThrow().getAvailableQuantity();
    }

    private ReserveTicketsRequest request(TicketType ticket, int quantity) {
        return ReserveTicketsRequest.builder().releaseId(UUID.randomUUID().toString())
                .tickets(List.of(ReserveTicketRequest.builder().ticketTypeId(ticket.getId())
                        .quantity(quantity).build())).build();
    }

    private TicketType ticket() {
        return new TransactionTemplate(transactionManager).execute(tx -> {
        Category category = categories.saveAndFlush(Category.builder().name(UUID.randomUUID().toString())
                .description("Test").isActive(true).build());
        Venue venue = venues.saveAndFlush(Venue.builder().name("Test").city("Colombo").address("Test").build());
        Event event = events.saveAndFlush(Event.builder().title("Test").description("Test")
                .category(category).venue(venue).startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(2)).status(EventStatus.PUBLISHED).createdBy("user").build());
        return tickets.saveAndFlush(TicketType.builder().event(event).name("Standard").price(BigDecimal.TEN)
                .totalQuantity(10).availableQuantity(5).build());
        });
    }
}
