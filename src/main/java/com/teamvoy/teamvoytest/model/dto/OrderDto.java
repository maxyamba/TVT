package com.teamvoy.teamvoytest.model.dto;

import com.teamvoy.teamvoytest.model.IPhoneOrder;
import com.teamvoy.teamvoytest.model.Order;
import com.teamvoy.teamvoytest.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer orderId;
    private Map<String, Integer> iPhoneWithQuantity;
    private LocalDateTime timestamp;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;

    public OrderDto(Order order) {
        orderId = order.getId();
        timestamp = order.getTimestamp();
        orderStatus = order.getStatus();
        totalPrice = order.getTotalPrice();
        iPhoneWithQuantity = new HashMap<>();
        for (IPhoneOrder iPhoneOrder : order.getIPhoneOrders()) {
            iPhoneWithQuantity.put(iPhoneOrder.getIPhone().getName(), iPhoneOrder.getQuantity());
        }
    }
}
