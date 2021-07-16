package com.delivery.service.controller;

import com.delivery.service.dto.OrderStatusDto;
import com.delivery.service.exceptions.ResourceNotFoundException;
import com.delivery.service.persistence.entities.Order;
import com.delivery.service.persistence.entities.enums.DeliveryStatus;
import com.delivery.service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderStatusDto changeOrderStatusToDelivered(@RequestBody Order order) {
        if (order.getId() == null || !orderService.existsById(order.getId())) {
            throw new ResourceNotFoundException("Order with id: " + order.getId() + " does not exist");
        }
        if (order.getStatus().equals(DeliveryStatus.PROCESSED)) return orderService.updateOrderStatusToDelivered(order);

        return orderService.updateOrderStatusToCompleted(order);
    }


    @GetMapping("/processed")
    public List<OrderStatusDto> findAllProcessedOrders(){
        return orderService.findAllByStatusProcessed(DeliveryStatus.PROCESSED);
    }

    @GetMapping("/delivered")
    public List<OrderStatusDto> findAllDeliveredOrders(){
        return orderService.findAllByStatusProcessed(DeliveryStatus.DELIVERED);
    }

    @GetMapping("/completed")
    public List<OrderStatusDto> findAllCompletedOrders(){
        return orderService.findAllByStatusProcessed(DeliveryStatus.COMPLETED);
    }

}
