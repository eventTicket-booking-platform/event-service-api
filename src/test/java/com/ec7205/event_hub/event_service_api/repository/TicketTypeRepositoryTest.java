package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.Category;
import com.ec7205.event_hub.event_service_api.entity.Event;
import com.ec7205.event_hub.event_service_api.entity.TicketType;
import com.ec7205.event_hub.event_service_api.entity.Venue;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class TicketTypeRepositoryTest {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Test
    void shouldReserveTicketsWhenEnoughTicketsAreAvailable() {

        Category category = categoryRepository.save(
                Category.builder()
                        .name("Test Category")
                        .description("Test category")
                        .isActive(true)
                        .build()
        );

        Venue venue = venueRepository.save(
                Venue.builder()
                        .name("Test Venue")
                        .city("Colombo")
                        .address("Test Address")
                        .build()
        );

        Event event = eventRepository.save(
                Event.builder()
                        .title("Test Event")
                        .description("Test event")
                        .category(category)
                        .venue(venue)
                        .startDateTime(LocalDateTime.now().plusDays(1))
                        .endDateTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .status(EventStatus.PUBLISHED)
                        .createdBy("test-user")
                        .build()
        );

        TicketType ticketType = ticketTypeRepository.save(
                TicketType.builder()
                        .name("Standard")
                        .price(BigDecimal.valueOf(2500))
                        .totalQuantity(10)
                        .availableQuantity(10)
                        .event(event)
                        .build()
        );

        int updatedRows =
                ticketTypeRepository.reserveTickets(ticketType.getId(), 3);

        TicketType updatedTicket =
                ticketTypeRepository.findById(ticketType.getId()).orElseThrow();

        assertEquals(1, updatedRows);
        assertEquals(7, updatedTicket.getAvailableQuantity());
    }

    @Test
    void shouldNotReserveTicketsWhenNotEnoughTicketsAreAvailable() {

        Category category = categoryRepository.save(
                Category.builder()
                        .name("Test Category 2")
                        .description("Test category")
                        .isActive(true)
                        .build()
        );

        Venue venue = venueRepository.save(
                Venue.builder()
                        .name("Test Venue 2")
                        .city("Colombo")
                        .address("Test Address")
                        .build()
        );

        Event event = eventRepository.save(
                Event.builder()
                        .title("Test Event 2")
                        .description("Test event")
                        .category(category)
                        .venue(venue)
                        .startDateTime(LocalDateTime.now().plusDays(1))
                        .endDateTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .status(EventStatus.PUBLISHED)
                        .createdBy("test-user")
                        .build()
        );

        TicketType ticketType = ticketTypeRepository.save(
                TicketType.builder()
                        .name("Standard")
                        .price(BigDecimal.valueOf(2500))
                        .totalQuantity(5)
                        .availableQuantity(5)
                        .event(event)
                        .build()
        );

        int updatedRows =
                ticketTypeRepository.reserveTickets(ticketType.getId(), 8);

        TicketType updatedTicket =
                ticketTypeRepository.findById(ticketType.getId()).orElseThrow();

        assertEquals(0, updatedRows);
        assertEquals(5, updatedTicket.getAvailableQuantity());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldPreventOversellingWhenMultipleRequestsReserveAtSameTime() throws Exception {

        Category category = categoryRepository.save(
                Category.builder()
                        .name("Concurrent Category")
                        .description("Test category")
                        .isActive(true)
                        .build()
        );

        Venue venue = Venue.builder()
                .name("Concurrent Venue")
                .city("Colombo")
                .address("Test Address")
                .build();

        Event event = eventRepository.save(
                Event.builder()
                        .title("Concurrent Event")
                        .description("Test event")
                        .category(category)
                        .venue(venue)
                        .startDateTime(LocalDateTime.now().plusDays(1))
                        .endDateTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .status(EventStatus.PUBLISHED)
                        .createdBy("test-user")
                        .build()
        );

        TicketType ticketType = ticketTypeRepository.save(
                TicketType.builder()
                        .name("Standard")
                        .price(BigDecimal.valueOf(2500))
                        .totalQuantity(10)
                        .availableQuantity(10)
                        .event(event)
                        .build()
        );

        Long ticketTypeId = ticketType.getId();

        int numberOfRequests = 20;

        ExecutorService executorService =
                Executors.newFixedThreadPool(numberOfRequests);

        CountDownLatch startLatch = new CountDownLatch(1);

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfRequests; i++) {

            futures.add(
                    executorService.submit(() -> {

                        startLatch.await();

                        return ticketTypeRepository.reserveTickets(
                                ticketTypeId,
                                1
                        );
                    })
            );
        }

        startLatch.countDown();

        int successCount = 0;

        for (Future<Integer> future : futures) {
            successCount += future.get();
        }

        executorService.shutdown();

        TicketType updatedTicket =
                ticketTypeRepository.findById(ticketTypeId).orElseThrow();

        assertEquals(10, successCount);
        assertEquals(0, updatedTicket.getAvailableQuantity());
    }
}