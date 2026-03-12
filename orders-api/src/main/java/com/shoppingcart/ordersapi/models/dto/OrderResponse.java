package com.shoppingcart.ordersapi.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private CustomerDTO customer;
    private double totalPrice;
    private String status;
    private List<OrderDetailsDTO> items;
}
