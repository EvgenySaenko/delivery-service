package com.delivery.service.services;

import com.delivery.service.dto.OrderDto;
import com.delivery.service.dto.OrderStatusDto;
import com.delivery.service.persistence.entities.Order;
import com.delivery.service.persistence.entities.enums.DeliveryStatus;
import com.delivery.service.repositories.OrderRepository;
import com.delivery.service.utils.converter.OrderConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter converter;
    private final ObjectMapper mapper;
    private OrderDto orderDto;
    private final AmqpTemplate amqpTemplate;

    @PostConstruct
    public void init(){
        this.orderDto = null;
    }


    @RabbitListener(queues = "delivery.queue")
    public void waitingForDeliveryOrder(Message message) {
        try{
            String orderMessage = new String(message.getBody(), StandardCharsets.UTF_8);
            orderDto = mapper.readValue(orderMessage, OrderDto.class);
            log.info("New delivery order received: " + orderDto.toString());
        }catch (Throwable th){
            log.error("Error when receiving order message",th);
        }
        Order order =  converter.convertOrderDtoToOrder(orderDto);
        saveOrUpdate(order);//сохраняем в базу пришедший заказ на доставку
    }


    public List<OrderStatusDto> findAllByStatusProcessed(DeliveryStatus ds){
        return orderRepository.findAllByStatus(ds).stream().map(converter::convertToOrderStatusDto).collect(Collectors.toList());
    }

    public OrderStatusDto saveOrUpdate(Order order) {
        return converter.convertToOrderStatusDto(orderRepository.save(order));
    }


    //изменяем статус доставки
    public OrderStatusDto updateOrderStatusToDelivered(Order order) {
        order.setStatus(DeliveryStatus.DELIVERED);
        OrderStatusDto orderStatusDto = converter.convertToOrderStatusDto(orderRepository.save(order));
        amqpTemplate.convertAndSend("onlineshop.exchange", "delivery.status", "Доставляется");
        return orderStatusDto;
    }

    //изменяем статус доставки
    public OrderStatusDto updateOrderStatusToCompleted(Order order) {
        order.setStatus(DeliveryStatus.COMPLETED);
        return converter.convertToOrderStatusDto(orderRepository.save(order));
    }


    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

}
