package com.teamvoy.teamvoytest.service.impl;

import com.teamvoy.teamvoytest.exception.ItemValidationException;
import com.teamvoy.teamvoytest.exception.NotFoundException;
import com.teamvoy.teamvoytest.model.IPhone;
import com.teamvoy.teamvoytest.model.IPhoneOrder;
import com.teamvoy.teamvoytest.model.Order;
import com.teamvoy.teamvoytest.model.dto.OrderDto;
import com.teamvoy.teamvoytest.model.enums.OrderStatus;
import com.teamvoy.teamvoytest.repository.IPhoneOrderRepository;
import com.teamvoy.teamvoytest.repository.IPhoneRepository;
import com.teamvoy.teamvoytest.repository.OrderRepository;
import com.teamvoy.teamvoytest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static final String IPHONE_IS_NOT_FOUND = "Invalid iPhone ID: ";
    public static final String ORDER_IS_NOT_FOUND = "Invalid order ID: ";
    public static final String QUANTITY_COMPARING = "Quantity in order is more than total iPhones in database";
    private final OrderRepository orderRepository;
    private final IPhoneRepository iPhoneRepository;
    private final IPhoneOrderRepository iPhoneOrderRepository;

    private final long ORDER_LIFE_TIME = 600_000L;

    @Override
    @Transactional
    @Modifying
    public OrderDto createOrder(Map<String, Integer> request) {

        Order order = new Order();
        Set<IPhoneOrder> iPhoneOrderSet = new HashSet<>();
        double totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : request.entrySet()) {
            Integer iPhoneId = Integer.valueOf(entry.getKey());
            Integer quantity = entry.getValue();

            IPhone iPhone = iPhoneRepository.findById(iPhoneId)
                    .orElseThrow(() -> new NotFoundException(IPHONE_IS_NOT_FOUND + iPhoneId));

            IPhoneOrder iPhoneOrder = new IPhoneOrder();
            iPhoneOrder.setIPhone(iPhone);
            iPhoneOrder.setQuantity(quantity);
            iPhoneOrder.setOrder(order);
            iPhoneOrderSet.add(iPhoneOrder);
            iPhone.setQuantity(calculateQuantity(iPhone.getQuantity(), quantity));
            totalPrice += quantity * iPhone.getPrice().doubleValue();
            iPhoneOrderRepository.save(iPhoneOrder);
        }
        order.setTotalPrice(BigDecimal.valueOf(totalPrice));
        order.setTimestamp(LocalDateTime.now());
        order.setIPhoneOrders(iPhoneOrderSet);
        order.setStatus(OrderStatus.UNPAID);

        return new OrderDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    @Modifying
    public OrderDto buyOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ORDER_IS_NOT_FOUND + orderId));
        order.setStatus(OrderStatus.PAID);
        return new OrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    private Integer calculateQuantity(Integer iPhoneTotalQuantity, Integer orderQuantity) {
        if (iPhoneTotalQuantity < orderQuantity)
            throw new ItemValidationException(QUANTITY_COMPARING);
        return iPhoneTotalQuantity - orderQuantity;
    }

    @Scheduled(fixedRate = ORDER_LIFE_TIME)
    @Transactional
    public void removeUnpaidOrders() {
        LocalDateTime timeAgo = LocalDateTime.now().minusSeconds(ORDER_LIFE_TIME / 1000);

        List<Order> unpaidOrders = orderRepository.findByStatusAndTimestampBefore(OrderStatus.UNPAID, timeAgo);
        List<IPhoneOrder> iPhoneOrders = iPhoneOrderRepository.findByOrderIn(unpaidOrders);

        List<IPhoneOrder> iPhoneOrderList = iPhoneOrders.stream().peek(iPhoneOrder -> {
            IPhone iPhone = iPhoneRepository.findById(iPhoneOrder.getIPhone().getId())
                    .orElseThrow(() -> new NotFoundException(IPHONE_IS_NOT_FOUND + iPhoneOrder.getIPhone().getId()));
            iPhone.setQuantity(iPhone.getQuantity() + iPhoneOrder.getQuantity());
        }).collect(Collectors.toList());
        iPhoneOrderRepository.deleteAll(iPhoneOrderList);
        orderRepository.deleteAll(unpaidOrders);
    }
}
