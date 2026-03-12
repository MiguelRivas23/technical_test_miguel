package com.shoppingcart.paymentsapi.models.dto;

import lombok.Data;

@Data
public class CardDTO {
    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
    private String cvv;
}
