package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.EventBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventBannerRepository extends JpaRepository<EventBanner, String> {

    @Query("select eb from EventBanner eb where eb.event.event_id = :eventId")
    Optional<EventBanner> findByEventId(@Param("eventId") Long eventId);
}
