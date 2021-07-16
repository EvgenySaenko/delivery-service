package com.delivery.service.repositories;


import com.delivery.service.persistence.entities.Order;
import com.delivery.service.persistence.entities.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByStatus(DeliveryStatus deliveryStatus);
}
