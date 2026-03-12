package com.shoppingcart.customerapi.models.dto;

import com.shoppingcart.customerapi.models.Address;
import com.shoppingcart.customerapi.models.Name;
import lombok.Data;

@Data
public class CustomerDTO {
    private Name name;
    private String email;
    private String phone;
    private Address address;
}
