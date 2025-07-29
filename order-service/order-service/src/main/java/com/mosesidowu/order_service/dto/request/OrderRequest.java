package com.mosesidowu.order_service.dto.request;

import lombok.Data;

@Data
public class OrderRequest {

    private String userId;
    private String productId;
    private int quantity;

}
