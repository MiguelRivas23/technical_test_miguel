package com.shoppingcart.ordersapi.models.dto;

import lombok.Data;

@Data
public class OrderDetailsDTO {
    private int numItem;
    private Long productId;
    private String title;
    private double price;
    private int quantity;
}
