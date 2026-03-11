package com.shoppingcart.securityapi.controller;

import com.shoppingcart.jwtvalidator.service.JwtService;
import com.shoppingcart.securityapi.models.User;
import com.shoppingcart.securityapi.models.dto.AuthRequest;
import com.shoppingcart.securityapi.models.dto.AuthResponse;
import com.shoppingcart.securityapi.models.dto.CustomerDTO;
import com.shoppingcart.securityapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok("User created successfully with customer id: " + savedUser.getCustomerId());
    }

    @GetMapping("me")
    public ResponseEntity<?> getMyCustomer(Authentication authentication){
        String username = authentication.getName();
        CustomerDTO customer = userService.getMyCustomer(username);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userService.findByUsername(request.getUsername());
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
