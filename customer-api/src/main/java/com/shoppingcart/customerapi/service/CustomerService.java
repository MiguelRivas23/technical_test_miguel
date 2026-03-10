package com.shoppingcart.customerapi.service;

import com.shoppingcart.customerapi.models.Customer;
import com.shoppingcart.customerapi.models.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO savedCustomer(Customer customer);
    CustomerDTO updateCustomer(Long id, Customer customer);
    boolean deleteCustomer(Long id);
}
