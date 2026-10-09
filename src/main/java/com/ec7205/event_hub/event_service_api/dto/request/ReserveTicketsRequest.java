package com.ec7205.event_hub.event_service_api.dto.request;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveTicketsRequest {

    // Optional for compatibility with existing callers; stable per booking release.
    @Size(max = 100)
    @Pattern(regexp = ".*\\S.*", message = "releaseId must not be blank")
    private String releaseId;

    @Valid
    @NotEmpty
    private List<ReserveTicketRequest> tickets;
}
