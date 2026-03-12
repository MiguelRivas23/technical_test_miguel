package com.shoppingcart.ordersapi.service;

import com.shoppingcart.ordersapi.constants.OrderStatus;
import com.shoppingcart.ordersapi.models.Order;
import com.shoppingcart.ordersapi.models.OrderDetails;
import com.shoppingcart.ordersapi.models.dto.OrderRequest;
import com.shoppingcart.ordersapi.models.dto.OrderResponse;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest, String username);
    OrderResponse viewOrderDetails(Long id, String username);
    void cancelOrder(Long id, String username);
    void completeOrder(Long id, String username);
}
