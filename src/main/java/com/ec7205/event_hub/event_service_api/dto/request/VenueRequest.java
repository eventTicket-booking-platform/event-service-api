package com.ec7205.event_hub.event_service_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class VenueRequest {

    @NotBlank(message = "Venue name is required")
    @Size(max = 150, message = "Venue name must not exceed 150 characters")
    private String name;

    @NotBlank(message = "Venue city is required")
    @Size(max = 100, message = "Venue city must not exceed 100 characters")
    private String city;

    @NotBlank(message = "Venue address is required")
    @Size(max = 255, message = "Venue address must not exceed 255 characters")
    private String address;
}
