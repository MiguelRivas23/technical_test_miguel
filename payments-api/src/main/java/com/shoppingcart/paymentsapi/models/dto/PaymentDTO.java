package com.shoppingcart.paymentsapi.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoppingcart.paymentsapi.constant.PaymentMehod;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long orderId;
    private Double change;
    private Double amount;
    private PaymentMehod paymentMethod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime paymentDate;
    private CardDTO cardInfo;
}
