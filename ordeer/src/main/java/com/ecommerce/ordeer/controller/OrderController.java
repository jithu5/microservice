package com.ecommerce.ordeer.controller;

import com.ecommerce.ordeer.entity.Order;
import com.ecommerce.ordeer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestHeader("X-User-ID") String userId){
        try {
            orderService.createOrder(userId);
            return ResponseEntity.ok().body("Order created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
