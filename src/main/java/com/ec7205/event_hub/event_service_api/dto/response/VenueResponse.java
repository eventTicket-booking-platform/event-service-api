package com.ec7205.event_hub.event_service_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueResponse {

    private Long venueId;
    private String name;
    private String city;
    private String address;
}
