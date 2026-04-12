package com.ec7205.event_hub.event_service_api.utils.enums;

public enum EventStatus {
    // Event is created but not yet visible to users.
    // Hosts/admins can still edit it before publishing.
    DRAFT,

    // Event is live and visible to users.
    // Users are allowed to view and book tickets for this event.
    PUBLISHED,

    // Event has been cancelled and is no longer available for booking.
    // Existing bookings may need refund/cancellation handling.
    CANCELLED,

    // Event has already finished successfully.
    // No more bookings are allowed, but event history remains available.
    COMPLETED
}