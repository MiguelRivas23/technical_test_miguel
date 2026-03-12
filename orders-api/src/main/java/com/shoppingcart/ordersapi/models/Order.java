package com.shoppingcart.ordersapi.models;

import com.shoppingcart.ordersapi.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerId;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
