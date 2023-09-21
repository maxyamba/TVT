package com.teamvoy.teamvoytest.controller;

import com.teamvoy.teamvoytest.model.dto.OrderDto;
import com.teamvoy.teamvoytest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody Map<String, Integer> request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PostMapping("/{id}")
    public ResponseEntity<OrderDto> buyOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.buyOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
