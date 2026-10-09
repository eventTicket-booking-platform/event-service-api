package com.ec7205.event_hub.event_service_api.service;

import com.ec7205.event_hub.event_service_api.config.*;
import com.ec7205.event_hub.event_service_api.controller.*;
import com.ec7205.event_hub.event_service_api.entity.Event;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.repository.*;
import com.ec7205.event_hub.event_service_api.service.impl.EventServiceImpl;
import com.ec7205.event_hub.event_service_api.utils.FileDataExtractor;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({AdminEventController.class, CategoryController.class})
@Import({SecurityConfig.class, JwtAuthConverter.class, EventServiceImpl.class, EventAccess.class})
@ActiveProfiles("test")
class EventAuthorizationHttpTest {
    @Autowired MockMvc mvc;
    @MockBean JwtDecoder decoder;
    @MockBean EventRepository events;
    @MockBean EventBannerRepository banners;
    @MockBean CategoryRepository categories;
    @MockBean EventMapper mapper;
    @MockBean FileService files;
    @MockBean FileDataExtractor extractor;
    @MockBean CategoryService categoryService;

    @Test void unauthenticatedManagementReturns401() throws Exception {
        mvc.perform(get("/event-service/api/v1/events/admin/all")).andExpect(status().isUnauthorized());
        mvc.perform(delete("/event-service/api/v1/events/1")).andExpect(status().isUnauthorized());
    }
    @Test void customerCannotUseManagementRoutes() throws Exception {
        token("customer", "CUSTOMER");
        mvc.perform(delete("/event-service/api/v1/events/1").header("Authorization", "Bearer customer"))
                .andExpect(status().isForbidden());
        mvc.perform(get("/event-service/api/v1/events/admin/owned-ids").header("Authorization", "Bearer customer"))
                .andExpect(status().isForbidden());
        verifyNoInteractions(events);
    }
    @Test void hostCanDeleteOwnEventButAnotherHostsEventReturns403() throws Exception {
        token("host-1", "HOST");
        when(events.findById(1L)).thenReturn(Optional.of(Event.builder().event_id(1L).createdBy("host-1").status(EventStatus.DRAFT).build()));
        when(events.findById(2L)).thenReturn(Optional.of(Event.builder().event_id(2L).createdBy("host-2").status(EventStatus.DRAFT).build()));
        mvc.perform(delete("/event-service/api/v1/events/1").header("Authorization", "Bearer host-1"))
                .andExpect(status().isOk());
        mvc.perform(delete("/event-service/api/v1/events/2").header("Authorization", "Bearer host-1"))
                .andExpect(status().isForbidden()).andExpect(jsonPath("$.code").value(403));
    }
    @Test void hostCannotManagePlatformCategoriesButAdminCan() throws Exception {
        token("host", "host");
        mvc.perform(delete("/event-service/api/v1/categories/1").header("Authorization", "Bearer host"))
                .andExpect(status().isForbidden());
        verifyNoInteractions(categoryService);
        token("admin", "ADMIN");
        mvc.perform(delete("/event-service/api/v1/categories/1").header("Authorization", "Bearer admin"))
                .andExpect(status().isOk());
        verify(categoryService).deleteCategory(1L);
    }
    private void token(String subject, String role) {
        when(decoder.decode(subject)).thenReturn(Jwt.withTokenValue(subject).header("alg", "none")
                .subject(subject).claim("realm_access", Map.of("roles", List.of(role))).build());
    }
}
