package com.ec7205.event_hub.event_service_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class TicketTypeRequest {

    private Long id;

    @NotBlank(message = "Ticket type name is required")
    @Size(max = 100, message = "Ticket type name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Ticket type price is required")
    @DecimalMin(value = "0.01", message = "Ticket type price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Total quantity is required")
    @Min(value = 0, message = "Total quantity must be zero or greater")
    private Integer totalQuantity;
}
