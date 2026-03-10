package com.shoppingcart.productapi.controller;

import com.shoppingcart.productapi.models.Products;
import com.shoppingcart.productapi.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("allProducts")
    public ResponseEntity<List<Products>> getProducts() {
        return ResponseEntity.ok(productsService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductsById(@PathVariable String id) {
        return ResponseEntity.ok(productsService.getProductById(id));
    }
}
