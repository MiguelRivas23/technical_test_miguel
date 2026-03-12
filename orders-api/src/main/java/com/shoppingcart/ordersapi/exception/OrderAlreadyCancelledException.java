package com.shoppingcart.ordersapi.exception;

public class OrderAlreadyCancelledException extends RuntimeException{
    public OrderAlreadyCancelledException(String message) {
        super(message);
    }
}
