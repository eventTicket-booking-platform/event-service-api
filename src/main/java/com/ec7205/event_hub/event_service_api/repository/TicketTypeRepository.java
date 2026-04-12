package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    List<TicketType> findByEventIdOrderByIdAsc(Long eventId);
}
