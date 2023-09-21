package com.teamvoy.teamvoytest.repository;

import com.teamvoy.teamvoytest.model.Order;
import com.teamvoy.teamvoytest.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatusAndTimestampBefore(OrderStatus status, LocalDateTime timestamp);
}
