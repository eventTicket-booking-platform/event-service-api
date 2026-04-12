package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
