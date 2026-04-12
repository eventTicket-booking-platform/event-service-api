package com.ec7205.event_hub.event_service_api.dto.response;

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
public class EventSummaryResponse {

    private Long eventId;
    private String title;
    private String categoryName;
    private String city;
    private String bannerUrl;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
