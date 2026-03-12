package com.shoppingcart.paymentsapi.exception;

public class OrderAlreadyCompletedException extends RuntimeException{
    public OrderAlreadyCompletedException(String message) {
        super(message);
    }
}
