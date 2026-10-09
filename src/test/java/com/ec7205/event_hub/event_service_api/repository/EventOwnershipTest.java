package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.config.EventAccess;
import com.ec7205.event_hub.event_service_api.dto.request.*;
import com.ec7205.event_hub.event_service_api.dto.response.AdminEventSummaryResponse;
import com.ec7205.event_hub.event_service_api.entity.*;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.service.FileService;
import com.ec7205.event_hub.event_service_api.service.impl.EventServiceImpl;
import com.ec7205.event_hub.event_service_api.utils.FileDataExtractor;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({EventServiceImpl.class, EventAccess.class})
class EventOwnershipTest {
    @Autowired EventServiceImpl service;
    @Autowired EventRepository events;
    @Autowired CategoryRepository categories;
    @MockBean EventMapper mapper;
    @MockBean FileService files;
    @MockBean FileDataExtractor fileDataExtractor;
    private Event own;
    private Event other;
    private Category category;

    @BeforeEach void setup() {
        category = categories.saveAndFlush(Category.builder().name("Test").isActive(true).build());
        own = event("host-1");
        other = event("host-2");
        when(mapper.toAdminEventSummaryResponse(any())).thenAnswer(call ->
                AdminEventSummaryResponse.builder().eventId(((Event) call.getArgument(0)).getEvent_id()).build());
    }
    @AfterEach void clearSecurity() { SecurityContextHolder.clearContext(); }

    @Test void adminCanManageAnotherHostsEventAndListAll() {
        login("administrator", "ADMIN");
        service.changeEventStatus(other.getEvent_id(), UpdateEventStatusRequest.builder().status(EventStatus.PUBLISHED).build());
        assertEquals(EventStatus.PUBLISHED, other.getStatus());
        assertDoesNotThrow(() -> service.getAdminEventDetails(other.getEvent_id()));
        assertEquals(2, service.getAdminEvents(null, null, PageRequest.of(0, 10)).getDataCount());
        service.deleteEvent(other.getEvent_id());
        assertEquals(EventStatus.CANCELLED, other.getStatus());
    }

    @Test void hostCanManageOwnEvent() {
        login("host-1", "HOST");
        service.updateEvent(own.getEvent_id(), updateRequest(), null);
        service.changeEventStatus(own.getEvent_id(), UpdateEventStatusRequest.builder().status(EventStatus.PUBLISHED).build());
        assertEquals(EventStatus.PUBLISHED, own.getStatus());
        assertDoesNotThrow(() -> service.getAdminEventDetails(own.getEvent_id()));
        service.deleteEvent(own.getEvent_id());
        assertEquals(EventStatus.CANCELLED, own.getStatus());
    }

    @Test void hostCannotUpdateDeleteChangeStatusOrReadAnotherHostsEvent() {
        login("host-1", "HOST");
        assertThrows(AccessDeniedException.class, () -> service.updateEvent(other.getEvent_id(), updateRequest(), null));
        assertThrows(AccessDeniedException.class, () -> service.deleteEvent(other.getEvent_id()));
        assertThrows(AccessDeniedException.class, () -> service.changeEventStatus(other.getEvent_id(),
                UpdateEventStatusRequest.builder().status(EventStatus.PUBLISHED).build()));
        assertThrows(AccessDeniedException.class, () -> service.getAdminEventDetails(other.getEvent_id()));
        assertEquals(EventStatus.DRAFT, other.getStatus());
    }

    @Test void hostListAndPaginationCountContainOnlyOwnedEvents() {
        login("host-1", "HOST");
        var page = service.getAdminEvents(null, null, PageRequest.of(0, 1));
        assertEquals(1, page.getDataCount());
        assertEquals(own.getEvent_id(), page.getDataList().get(0).getEventId());
        assertEquals(List.of(own.getEvent_id()), service.getOwnedEventIds());
    }

    @Test void createIgnoresForgedOwnerAndUsesJwtSubject() {
        login("host-1", "HOST");
        when(mapper.toVenueEntity(any())).thenReturn(Venue.builder().name("New").city("Colombo").address("Test").build());
        when(mapper.toTicketTypeEntities(any(), any())).thenReturn(List.of());
        CreateEventRequest request = CreateEventRequest.builder().title("New").description("Test")
                .categoryId(category.getId()).createdBy("host-2").venue(VenueRequest.builder().build())
                .startDateTime(LocalDateTime.now().plusDays(1)).endDateTime(LocalDateTime.now().plusDays(2))
                .ticketTypes(List.of(TicketTypeRequest.builder().build())).build();
        Long id = service.createEvent(request, null).getResourceId();
        assertEquals("host-1", events.findById(id).orElseThrow().getCreatedBy());
    }

    @Test void customerCannotUseManagementOperationsEvenOnOwnEvent() {
        login("host-1", "CUSTOMER");
        assertThrows(AccessDeniedException.class, () -> service.deleteEvent(own.getEvent_id()));
        assertThrows(AccessDeniedException.class, () -> service.getAdminEvents(null, null, PageRequest.of(0, 10)));
        assertThrows(AccessDeniedException.class, () -> service.getOwnedEventIds());
        assertThrows(AccessDeniedException.class, () -> service.createEvent(null, null));
    }

    private Event event(String owner) {
        return events.saveAndFlush(Event.builder().title("Event").description("Test").category(category)
                .venue(Venue.builder().name("Venue").city("Colombo").address("Test").build())
                .startDateTime(LocalDateTime.now().plusDays(1)).endDateTime(LocalDateTime.now().plusDays(2))
                .status(EventStatus.DRAFT).createdBy(owner).build());
    }
    private UpdateEventRequest updateRequest() {
        return UpdateEventRequest.builder().title("Updated").description("Test").categoryId(category.getId())
                .startDateTime(LocalDateTime.now().plusDays(1)).endDateTime(LocalDateTime.now().plusDays(2))
                .venue(VenueRequest.builder().build()).ticketTypes(List.of(TicketTypeRequest.builder().build())).build();
    }
    private void login(String subject, String role) {
        Jwt jwt = Jwt.withTokenValue("token").header("alg", "none").subject(subject).build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt,
                List.of(new SimpleGrantedAuthority(role))));
    }
}
