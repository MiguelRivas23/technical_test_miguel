package com.shoppingcart.ordersapi.repository;

import com.shoppingcart.ordersapi.constants.OrderStatus;
import com.shoppingcart.ordersapi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
