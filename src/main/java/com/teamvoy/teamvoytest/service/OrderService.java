package com.teamvoy.teamvoytest.service;

import com.teamvoy.teamvoytest.model.dto.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDto createOrder(Map<String, Integer> request);

    OrderDto buyOrder(Integer orderId);

    List<OrderDto> getAllOrders();
}
