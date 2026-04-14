package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @EntityGraph(attributePaths = {"category", "venue", "ticketTypes", "eventBanner"})
    @Query("select e from Event e where e.event_id = :id")
    Optional<Event> findDetailedById(@Param("id") Long id);
}
