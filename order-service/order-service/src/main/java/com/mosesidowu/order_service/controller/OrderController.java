package com.mosesidowu.order_service.controller;

import com.mosesidowu.order_service.client.UserClient;
import com.mosesidowu.order_service.data.model.Order;
import com.mosesidowu.order_service.dto.request.OrderRequest;
import com.mosesidowu.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final UserClient userClient;
    private final OrderService orderService;


    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(
            @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String userId) {
        userClient.getUser(userId);
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
