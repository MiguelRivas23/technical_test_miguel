package com.shoppingcart.productapi.models;

import lombok.Data;

@Data
public class Products {
    private long id;
    private String title;
    private double price;
    private String description;
    private String category;
}
