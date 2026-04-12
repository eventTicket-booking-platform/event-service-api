package com.ec7205.event_hub.event_service_api.dto.request;

import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateEventRequest {

    @NotBlank(message = "Event title is required")
    @Size(max = 150, message = "Event title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Event description is required")
    private String description;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Venue is required")
    @Valid
    private VenueRequest venue;

    @Size(max = 500, message = "Banner URL must not exceed 500 characters")
    private String bannerUrl;

    @NotNull(message = "Event start date and time is required")
    private LocalDateTime startDateTime;

    @NotNull(message = "Event end date and time is required")
    private LocalDateTime endDateTime;

    private EventStatus status;

    @NotNull(message = "Created by user id is required")
    private Long createdBy;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<TicketTypeRequest> ticketTypes;
}
