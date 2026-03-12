package com.shoppingcart.ordersapi.controller;

import com.shoppingcart.ordersapi.exception.OrderAlreadyCancelledException;
import com.shoppingcart.ordersapi.exception.OrderAlreadyCompletedException;
import com.shoppingcart.ordersapi.exception.OrderNotFoundException;
import com.shoppingcart.ordersapi.models.dto.OrderRequest;
import com.shoppingcart.ordersapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(orderService.createOrder(orderRequest, username));
    }

    @GetMapping("viewOrderDetails/{id}")
    public ResponseEntity<?> viewOrderDetails(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        try {
            return ResponseEntity.ok(orderService.viewOrderDetails(id, username));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Order not found");
        }
    }

    @PutMapping("cancelOrder/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        try {
            orderService.cancelOrder(id, username);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OrderAlreadyCompletedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (OrderAlreadyCancelledException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error");
        }
    }

    @PutMapping("completeOrder/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        try {
            orderService.completeOrder(id, username);
            return ResponseEntity.ok("Order completed successfully");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
