package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    @Query("select tt from TicketType tt where tt.event.event_id = :eventId order by tt.id asc")
    List<TicketType> findByEventIdOrderByIdAsc(@Param("eventId") Long eventId);
}
