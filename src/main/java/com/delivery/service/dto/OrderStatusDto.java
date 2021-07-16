package com.delivery.service.dto;

import com.delivery.service.persistence.entities.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
    private Long id;
    private String firstName;
    private String addressFrom;
    private String addressTo;
    private BigDecimal totalPrice;
    private String creationDateTime;
    private DeliveryStatus status;
}
