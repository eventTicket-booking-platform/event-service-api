package com.ec7205.event_hub.event_service_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketTypeResponse {

    private Long ticketTypeId;
    private String name;
    private BigDecimal price;
    private Integer totalQuantity;
}
