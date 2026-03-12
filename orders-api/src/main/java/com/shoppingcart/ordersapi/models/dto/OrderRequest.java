package com.shoppingcart.ordersapi.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private long customerId;
    private List<OrderItemRequest> items;
}
