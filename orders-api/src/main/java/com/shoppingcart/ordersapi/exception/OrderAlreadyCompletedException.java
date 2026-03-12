package com.shoppingcart.ordersapi.exception;

public class OrderAlreadyCompletedException extends RuntimeException{
    public OrderAlreadyCompletedException(String message) {
        super(message);
    }
}
