package com.shoppingcart.paymentsapi.service;

import com.shoppingcart.paymentsapi.models.Payment;
import com.shoppingcart.paymentsapi.models.dto.PaymentDTO;
import com.shoppingcart.paymentsapi.models.dto.PaymentResponse;

public interface PaymentService {
    Payment payOrder(PaymentDTO paymentDTO, String username);
    PaymentResponse getPaymentByOrderId(Long orderId, String username);
}
