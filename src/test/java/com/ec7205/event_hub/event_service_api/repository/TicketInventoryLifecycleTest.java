package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.config.EventAccess;
import com.ec7205.event_hub.event_service_api.dto.request.*;
import com.ec7205.event_hub.event_service_api.entity.Category;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.mapper.CategoryMapper;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.service.FileService;
import com.ec7205.event_hub.event_service_api.service.impl.EventServiceImpl;
import com.ec7205.event_hub.event_service_api.service.impl.InternalEventServiceImpl;
import com.ec7205.event_hub.event_service_api.utils.FileDataExtractor;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
@Import({EventServiceImpl.class, InternalEventServiceImpl.class, EventMapper.class, CategoryMapper.class, EventAccess.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class TicketInventoryLifecycleTest {
    @Autowired EventServiceImpl service;
    @Autowired InternalEventServiceImpl inventory;
    @Autowired EventRepository events;
    @Autowired TicketTypeRepository tickets;
    @Autowired CategoryRepository categories;
    @MockBean FileService files;
    @MockBean FileDataExtractor fileDataExtractor;
    private Long categoryId;

    @BeforeEach void setup() {
        events.deleteAll();
        categories.deleteAll();
        categoryId = categories.saveAndFlush(Category.builder().name("Inventory").isActive(true).build()).getId();
        Jwt jwt = Jwt.withTokenValue("test").header("alg", "none").subject("host-1").build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt,
                List.of(new SimpleGrantedAuthority("HOST"))));
    }

    @AfterEach void clearSecurity() { SecurityContextHolder.clearContext(); }

    @ParameterizedTest
    @CsvSource({"120,60", "80,20", "60,0"})
    void editPreservesCommittedStockAndTierId(int newTotal, int expectedAvailable) {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = reserve(eventId, 60);
        TicketTypeRequest changed = tier(ticketId, newTotal);
        changed.setName("Renamed");
        changed.setPrice(BigDecimal.valueOf(25));
        service.updateEvent(eventId, update(changed), null);
        var response = service.getPublicEventDetails(eventId).getTicketTypes().get(0);
        assertEquals(ticketId, response.getTicketTypeId());
        assertEquals(newTotal, response.getTotalQuantity());
        assertEquals(expectedAvailable, response.getAvailableQuantity());
        assertEquals("Renamed", response.getName());
        assertEquals(0, BigDecimal.valueOf(25).compareTo(response.getPrice()));
    }

    @Test void rejectsTotalBelowCommittedStock() {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = reserve(eventId, 60);
        assertThrows(ConflictException.class, () -> service.updateEvent(eventId, update(tier(ticketId, 50)), null));
        assertInventory(ticketId, 100, 40);
    }

    @Test void addsNewTierWithFullAvailabilityAndKeepsExistingId() {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = reserve(eventId, 60);
        service.updateEvent(eventId, update(tier(ticketId, 100), tier(null, 15)), null);
        var tiers = service.getPublicEventDetails(eventId).getTicketTypes();
        assertEquals(2, tiers.size());
        var added = tiers.stream().filter(t -> !t.getTicketTypeId().equals(ticketId)).findFirst().orElseThrow();
        assertEquals(15, added.getTotalQuantity());
        assertEquals(15, added.getAvailableQuantity());
        assertInventory(ticketId, 100, 40);
    }

    @Test void removesOmittedUnsoldTier() {
        Long eventId = createEvent(tier(null, 100), tier(null, 20));
        var tiers = service.getPublicEventDetails(eventId).getTicketTypes();
        Long kept = tiers.get(0).getTicketTypeId();
        Long removed = tiers.get(1).getTicketTypeId();
        service.updateEvent(eventId, update(tier(kept, 100)), null);
        assertFalse(tickets.existsById(removed));
        assertEquals(kept, service.getPublicEventDetails(eventId).getTicketTypes().get(0).getTicketTypeId());
    }

    @Test void rejectsRemovalOfSoldOrReservedTier() {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = reserve(eventId, 60);
        assertThrows(ConflictException.class, () -> service.updateEvent(eventId, update(tier(null, 100)), null));
        assertInventory(ticketId, 100, 40);
        assertEquals(1, service.getPublicEventDetails(eventId).getTicketTypes().size());
    }

    @Test void rejectsForeignTierId() {
        Long eventId = createEvent(tier(null, 100));
        Long foreignEvent = createEvent(tier(null, 30));
        Long foreignId = service.getPublicEventDetails(foreignEvent).getTicketTypes().get(0).getTicketTypeId();
        assertThrows(ConflictException.class, () -> service.updateEvent(eventId, update(tier(foreignId, 90)), null));
        assertInventory(foreignId, 30, 30);
        assertEquals("Inventory event", service.getPublicEventDetails(eventId).getTitle());
    }

    @Test void rejectsDuplicateTierIds() {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = service.getPublicEventDetails(eventId).getTicketTypes().get(0).getTicketTypeId();
        assertThrows(ConflictException.class, () -> service.updateEvent(eventId,
                update(tier(ticketId, 120), tier(ticketId, 130)), null));
        assertInventory(ticketId, 100, 100);
    }

    @Test void invalidLaterTierLeavesAllInventoryAndEventMetadataUnchanged() {
        Long eventId = createEvent(tier(null, 100), tier(null, 100));
        var tiers = service.getPublicEventDetails(eventId).getTicketTypes();
        Long first = tiers.get(0).getTicketTypeId();
        Long second = tiers.get(1).getTicketTypeId();
        inventory.reserveTickets(eventId, ReserveTicketsRequest.builder().tickets(List.of(
                ReserveTicketRequest.builder().ticketTypeId(second).quantity(60).build())).build());
        assertThrows(ConflictException.class, () -> service.updateEvent(eventId,
                update(tier(first, 120), tier(second, 50)), null));
        assertInventory(first, 100, 100);
        assertInventory(second, 100, 40);
        var unchanged = service.getPublicEventDetails(eventId);
        assertEquals("Inventory event", unchanged.getTitle());
        assertEquals("Venue", unchanged.getVenue().getName());
    }

    @Test void laterFailureRollsBackTierAndMetadataChanges() {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = reserve(eventId, 60);
        when(files.createResource(any(), anyString(), anyString())).thenThrow(new IllegalStateException("Upload failed"));
        var banner = new MockMultipartFile("bannerimg", "banner.png", "image/png", new byte[] {1});
        assertThrows(IllegalStateException.class, () -> service.updateEvent(eventId, update(tier(ticketId, 120)), banner));
        assertInventory(ticketId, 100, 40);
        assertEquals("Inventory event", service.getPublicEventDetails(eventId).getTitle());
        assertEquals("Venue", service.getPublicEventDetails(eventId).getVenue().getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 0})
    void newEventTierStartsWithItsFullQuantity(int quantity) {
        Long eventId = createEvent(tier(null, quantity));
        Long ticketId = service.getPublicEventDetails(eventId).getTicketTypes().get(0).getTicketTypeId();
        var stored = tickets.findById(ticketId).orElseThrow();
        assertEquals(quantity, stored.getTotalQuantity());
        assertEquals(quantity, stored.getAvailableQuantity());
    }

    private Long createEvent(TicketTypeRequest... tiers) {
        return service.createEvent(CreateEventRequest.builder().title("Inventory event").description("Test")
                .categoryId(categoryId).venue(venue()).status(EventStatus.PUBLISHED)
                .startDateTime(LocalDateTime.now().plusDays(1)).endDateTime(LocalDateTime.now().plusDays(2))
                .ticketTypes(List.of(tiers)).build(), null).getResourceId();
    }

    @Test void publicAndBookingInfoExposeRemainingQuantityAfterReservation() throws Exception {
        Long eventId = createEvent(tier(null, 100));
        Long ticketId = service.getPublicEventDetails(eventId).getTicketTypes().get(0).getTicketTypeId();
        inventory.reserveTickets(eventId, ReserveTicketsRequest.builder().tickets(List.of(
                ReserveTicketRequest.builder().ticketTypeId(ticketId).quantity(3).build())).build());
        var response = service.getPublicEventDetails(eventId).getTicketTypes().get(0);
        assertEquals(100, response.getTotalQuantity());
        assertEquals(97, response.getAvailableQuantity());
        var json = new com.fasterxml.jackson.databind.ObjectMapper().valueToTree(response);
        assertEquals(97, json.get("availableQuantity").asInt());
        assertEquals(100, json.get("totalQuantity").asInt());
        assertEquals(97, inventory.getBookingInfo(eventId).getTicketTypes().get(0).getAvailableQuantity());
    }

    private TicketTypeRequest tier(Long id, int total) {
        return TicketTypeRequest.builder().id(id).name("Standard").price(BigDecimal.TEN).totalQuantity(total).build();
    }

    private Long reserve(Long eventId, int quantity) {
        Long ticketId = service.getPublicEventDetails(eventId).getTicketTypes().get(0).getTicketTypeId();
        inventory.reserveTickets(eventId, ReserveTicketsRequest.builder().tickets(List.of(
                ReserveTicketRequest.builder().ticketTypeId(ticketId).quantity(quantity).build())).build());
        return ticketId;
    }

    private UpdateEventRequest update(TicketTypeRequest... tiers) {
        return UpdateEventRequest.builder().title("Updated event").description("Changed")
                .categoryId(categoryId).venue(VenueRequest.builder().name("Changed venue").city("Colombo").address("Changed").build())
                .startDateTime(LocalDateTime.now().plusDays(1)).endDateTime(LocalDateTime.now().plusDays(2))
                .ticketTypes(List.of(tiers)).build();
    }

    private void assertInventory(Long id, int total, int available) {
        var stored = tickets.findById(id).orElseThrow();
        assertEquals(total, stored.getTotalQuantity());
        assertEquals(available, stored.getAvailableQuantity());
    }

    private VenueRequest venue() {
        return VenueRequest.builder().name("Venue").city("Colombo").address("Test address").build();
    }
}
