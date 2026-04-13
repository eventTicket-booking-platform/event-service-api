package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.EventBanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventBannerRepository extends JpaRepository<EventBanner, String> {

    Optional<EventBanner> findByEvent_Event_id(Long eventId);
}
