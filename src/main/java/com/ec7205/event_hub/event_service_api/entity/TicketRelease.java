package com.ec7205.event_hub.event_service_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_releases")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRelease {
    @Id
    @Column(length = 100)
    private String releaseId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false, columnDefinition = "text")
    private String ticketQuantities;
}
