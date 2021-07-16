package com.delivery.service.utils.converter;


import com.delivery.service.dto.OrderDto;
import com.delivery.service.dto.OrderStatusDto;
import com.delivery.service.persistence.entities.Order;
import com.delivery.service.persistence.entities.enums.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private DateTimeFormatter formatter;

    @PostConstruct
    public void init(){
        this.formatter = DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy");

    }


    //пришедший заказ на доставку преобразуем к заказу
    public Order convertOrderDtoToOrder(OrderDto orderDto){
        return Order.builder()
                .firstName(orderDto.getFirstName())
                .addressFrom(orderDto.getAddressFrom())
                .addressTo(orderDto.getAddressTo())
                .totalPrice(BigDecimal.valueOf(orderDto.getTotalPrice().doubleValue()))
                .creationDateTime(formatter.format(LocalDateTime.now()))
                .status(DeliveryStatus.PROCESSED)
                .build();
    }

    public OrderStatusDto convertToOrderStatusDto(Order order){
        return OrderStatusDto.builder()
                .id(order.getId())
                .firstName(order.getFirstName())
                .addressFrom(order.getAddressFrom())
                .addressTo(order.getAddressTo())
                .totalPrice(order.getTotalPrice())
                .creationDateTime(order.getCreationDateTime())
                .status(order.getStatus())
        .build();
    }

}
