package com.mosesidowu.order_service.data.repository;


import com.mosesidowu.order_service.data.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
