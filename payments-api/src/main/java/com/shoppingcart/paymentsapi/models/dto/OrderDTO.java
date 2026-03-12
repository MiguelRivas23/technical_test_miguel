package com.shoppingcart.paymentsapi.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Object customer;
    private double totalPrice;
    private String status;
    private List<Object> items;
}
