package com.mosesidowu.order_service.controller;

import com.mosesidowu.order_service.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/{orderId}/user/{userId}")
    public ResponseEntity<String> getOrderDetails(@PathVariable String orderId, @PathVariable String userId) {
        String userInfo = userClient.getUser(userId);
        return ResponseEntity.ok("Order ID: " + orderId + " placed by: " + userInfo);
    }
}
