package com.shoppingcart.ordersapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orderId;
    private int numItem;
    private long productId;
    private String title;
    private int quantity;
    private double price;
}
