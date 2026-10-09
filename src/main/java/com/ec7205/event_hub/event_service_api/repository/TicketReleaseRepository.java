package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.TicketRelease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketReleaseRepository extends JpaRepository<TicketRelease, String> {
}
