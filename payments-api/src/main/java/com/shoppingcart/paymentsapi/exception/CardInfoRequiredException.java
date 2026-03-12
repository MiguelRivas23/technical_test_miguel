package com.shoppingcart.paymentsapi.exception;

public class CardInfoRequiredException extends RuntimeException {
    public CardInfoRequiredException(String message) {
        super(message);
    }
}
