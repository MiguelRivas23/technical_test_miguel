package com.shoppingcart.paymentsapi.controller;

import com.shoppingcart.paymentsapi.models.Payment;
import com.shoppingcart.paymentsapi.models.dto.PaymentDTO;
import com.shoppingcart.paymentsapi.models.dto.PaymentResponse;
import com.shoppingcart.paymentsapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("payOrder")
    public ResponseEntity<?> payOrder(@RequestBody PaymentDTO paymentDTO, @RequestHeader("Authorization") String token) {
        try {
            Payment payment = paymentService.payOrder(paymentDTO, token);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/byOrder/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentWithOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String token) {
        PaymentResponse response = paymentService.getPaymentByOrderId(orderId, token);
        return ResponseEntity.ok(response);
    }
}
