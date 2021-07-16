package com.delivery.service.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeliveryStatus {

    PROCESSED("ОЖИДАЕТ ДОСТАВКИ"),
    DELIVERED("ДОСТАВЛЯЕТСЯ"),
    COMPLETED("ДОСТАВЛЕН");


    @Getter
    private String name;

}