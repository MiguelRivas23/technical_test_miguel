package com.shoppingcart.securityapi.models.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Object name;
    private String email;
    private String phone;
    private Object address;
}
