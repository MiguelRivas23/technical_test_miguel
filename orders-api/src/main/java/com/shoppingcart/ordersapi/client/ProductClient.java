package com.shoppingcart.ordersapi.client;

import com.shoppingcart.ordersapi.models.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${produc.api.url}")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable long id);
}
