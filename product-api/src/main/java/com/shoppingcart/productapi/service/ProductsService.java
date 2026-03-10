package com.shoppingcart.productapi.service;

import com.shoppingcart.productapi.models.Products;

import java.util.List;

public interface ProductsService {
    List<Products> getProducts();
    Products getProductById(String id);
}
