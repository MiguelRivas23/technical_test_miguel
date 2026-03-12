package com.shoppingcart.paymentsapi.client;

import com.shoppingcart.paymentsapi.models.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-api", url = "${order.api.url}")
public interface OrderClient {
    @PutMapping("/completeOrder/{id}")
    void completeOrder(@PathVariable long id, @RequestHeader("Authorization") String token);

    @GetMapping("/viewOrderDetails/{id}")
    ResponseEntity<OrderDTO> viewOrderDetails(@PathVariable long id, @RequestHeader("Authorization") String token);
}
