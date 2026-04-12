package com.ec7205.event_hub.event_service_api.dto.request;

import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import jakarta.validation.constraints.NotNull;
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
public class UpdateEventStatusRequest {

    @NotNull(message = "Event status is required")
    private EventStatus status;
}
