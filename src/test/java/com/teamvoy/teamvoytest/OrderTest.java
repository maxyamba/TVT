package com.teamvoy.teamvoytest;

import com.teamvoy.teamvoytest.model.IPhone;
import com.teamvoy.teamvoytest.model.IPhoneOrder;
import com.teamvoy.teamvoytest.model.Order;
import com.teamvoy.teamvoytest.model.dto.OrderDto;
import com.teamvoy.teamvoytest.model.enums.OrderStatus;
import com.teamvoy.teamvoytest.repository.IPhoneOrderRepository;
import com.teamvoy.teamvoytest.repository.IPhoneRepository;
import com.teamvoy.teamvoytest.repository.OrderRepository;
import com.teamvoy.teamvoytest.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OrderTest {

    private final IPhone iPhone = new IPhone();
    private final IPhoneOrder iPhoneOrder = new IPhoneOrder();

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private IPhoneRepository iPhoneRepository;

    @Mock
    private IPhoneOrderRepository iPhoneOrderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        iPhone.setId(1);
        iPhone.setPrice(new BigDecimal("999.99"));
        iPhone.setQuantity(5);

        iPhoneOrder.setIPhone(iPhone);
        iPhoneOrder.setQuantity(2);
    }

    @Test
    public void testCreateOrder() {
        Map<String, Integer> request = new HashMap<>();
        request.put("1", 2);

        Order order = new Order();
        Set<IPhoneOrder> iPhoneOrders = new HashSet<>();
        iPhoneOrders.add(iPhoneOrder);
        order.setIPhoneOrders(iPhoneOrders);
        order.setTotalPrice(new BigDecimal("1999.98"));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.UNPAID);

        when(iPhoneRepository.findById(1)).thenReturn(Optional.of(iPhone));
        when(iPhoneOrderRepository.save(any(IPhoneOrder.class))).thenReturn(iPhoneOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.createOrder(request);

        verify(iPhoneRepository, times(1)).findById(1);
        verify(iPhoneOrderRepository, times(1)).save(any(IPhoneOrder.class));
        verify(orderRepository, times(1)).save(any(Order.class));

        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getTotalPrice(), result.getTotalPrice());
        assertEquals(order.getTimestamp(), result.getTimestamp());
        assertEquals(order.getStatus(), result.getOrderStatus());
        assertEquals(1, result.getIPhoneWithQuantity().size());
    }

    @Test
    public void testBuyOrder() {
        Order order = new Order();
        order.setId(1);
        order.setStatus(OrderStatus.UNPAID);

        Set<IPhoneOrder> iPhoneOrders = new HashSet<>();
        iPhoneOrders.add(iPhoneOrder);
        order.setIPhoneOrders(iPhoneOrders);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        OrderDto result = orderService.buyOrder(1);

        verify(orderRepository, times(1)).findById(1);

        assertEquals(order.getId(), result.getOrderId());
        assertEquals(OrderStatus.PAID, result.getOrderStatus());
    }
}