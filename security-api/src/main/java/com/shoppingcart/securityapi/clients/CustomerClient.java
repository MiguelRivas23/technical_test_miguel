package com.shoppingcart.securityapi.clients;

import com.shoppingcart.securityapi.models.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-api", url = "${customer.api.url}")
public interface CustomerClient {
    @GetMapping("/customerById/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}
