package com.shoppingcart.productapi.service;

import com.shoppingcart.productapi.client.FakeStoreProductClient;
import com.shoppingcart.productapi.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService{

    @Autowired
    private FakeStoreProductClient fakeStoreProductClient;

    @Override
    public List<Products> getProducts() {
        return fakeStoreProductClient.getProduct();
    }

    @Override
    public Products getProductById(String id) {
        return fakeStoreProductClient.getProductById(id);
    }
}
