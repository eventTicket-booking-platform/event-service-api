package com.ec7205.event_hub.event_service_api.dto.request;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveTicketsRequest {

    @Valid
    @NotEmpty
    private List<ReserveTicketRequest> tickets;
}