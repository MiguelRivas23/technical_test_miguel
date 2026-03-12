package com.shoppingcart.paymentsapi.models;

import com.shoppingcart.paymentsapi.constant.PaymentMehod;
import com.shoppingcart.paymentsapi.models.dto.CardDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private PaymentMehod paymentMethod;
    private double amount;
    private LocalDateTime paymentDate;
    private double change;
}
