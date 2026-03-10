package com.shoppingcart.productapi.client;

import com.shoppingcart.productapi.models.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "fake-store-product-client", url = "${fake.store.product.client.url}")
public interface FakeStoreProductClient {
    @GetMapping("/products")
    List<Products> getProduct();

    @GetMapping("/products/{id}")
    Products getProductById(@PathVariable String id);
}
