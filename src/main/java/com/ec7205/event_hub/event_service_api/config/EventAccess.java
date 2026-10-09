package com.ec7205.event_hub.event_service_api.config;

import com.ec7205.event_hub.event_service_api.entity.Event;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class EventAccess {
    public String managerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken jwt) || !auth.isAuthenticated()
                || (!hasRole("ADMIN") && !hasRole("HOST"))
                || jwt.getToken().getSubject() == null || jwt.getToken().getSubject().isBlank()) {
            throw new AccessDeniedException("Admin or host access is required");
        }
        return jwt.getToken().getSubject();
    }

    public boolean isAdmin() {
        managerId();
        return hasRole("ADMIN");
    }

    public void checkOwner(Event event) {
        String subject = managerId();
        if (!hasRole("ADMIN") && !subject.equals(event.getCreatedBy())) {
            throw new AccessDeniedException("You do not own this event");
        }
    }

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(authority ->
                authority.getAuthority().toUpperCase(Locale.ROOT).replaceFirst("^ROLE_", "").equals(role));
    }
}
