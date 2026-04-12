package com.ec7205.event_hub.event_service_api.dto.response;

import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEventSummaryResponse {

    private Long eventId;
    private String title;
    private String categoryName;
    private String city;
    private String bannerUrl;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private EventStatus status;
}
