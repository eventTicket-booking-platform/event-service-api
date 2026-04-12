package com.ec7205.event_hub.event_service_api.dto.response;

import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDetailResponse {

    private Long eventId;
    private String title;
    private String description;
    private CategoryResponse category;
    private VenueResponse venue;
    private String bannerUrl;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private EventStatus status;
    private List<TicketTypeResponse> ticketTypes;
}
