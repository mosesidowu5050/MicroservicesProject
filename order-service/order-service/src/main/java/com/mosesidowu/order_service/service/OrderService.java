package com.mosesidowu.order_service.service;

import com.mosesidowu.order_service.data.model.Order;
import com.mosesidowu.order_service.dto.request.OrderRequest;

import java.util.List;

public interface OrderService {

    Order placeOrder(OrderRequest request);

    List<Order> getOrdersByUser(String userId);

}
