package com.shoppingcart.customerapi.service;

import com.shoppingcart.customerapi.models.Customer;
import com.shoppingcart.customerapi.models.dto.CustomerDTO;
import com.shoppingcart.customerapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerDTO mapToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    public CustomerDTO savedCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if(existingCustomer != null){
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setPhone(customer.getPhone());
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return mapToDTO(updatedCustomer);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if(existingCustomer != null){
            customerRepository.delete(existingCustomer);
            return true;
        } else {
            return false;
        }
    }
}
