package com.shoppingcart.securityapi.service;

import com.shoppingcart.securityapi.models.User;
import com.shoppingcart.securityapi.models.dto.CustomerDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User findByUsername(String username);
    UserDetails loadUserByUsername(String username);
    User registerUser(User user);
    CustomerDTO getMyCustomer(String username);
}
