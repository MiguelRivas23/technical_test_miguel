package com.shoppingcart.customerapi.Controller;

import com.shoppingcart.customerapi.models.Customer;
import com.shoppingcart.customerapi.models.dto.CustomerDTO;
import com.shoppingcart.customerapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "allCustomers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping(value = "customerById/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        try {
            return ResponseEntity.ok(customerDTO);
        } catch (Exception e){
            return ResponseEntity.status(404).body("Customer not found");
        }
    }

    @PostMapping(value = "saveCustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer){
        customerService.savedCustomer(customer);
        return ResponseEntity.ok("Customer saved successfully");
    }

    @PutMapping(value = "updateCustomer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        customerService.updateCustomer(id, customer);
        try {
            return ResponseEntity.ok("Customer updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Customer not found");
        }
    }

    @DeleteMapping(value = "deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.ok("Customer deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Customer not found");
        }
    }
}
