package com.shoppingcart.paymentsapi.service;

import com.shoppingcart.jwtvalidator.service.JwtService;
import com.shoppingcart.paymentsapi.client.OrderClient;
import com.shoppingcart.paymentsapi.constant.PaymentMehod;
import com.shoppingcart.paymentsapi.exception.*;
import com.shoppingcart.paymentsapi.models.Payment;
import com.shoppingcart.paymentsapi.models.dto.CardDTO;
import com.shoppingcart.paymentsapi.models.dto.OrderDTO;
import com.shoppingcart.paymentsapi.models.dto.PaymentDTO;
import com.shoppingcart.paymentsapi.models.dto.PaymentResponse;
import com.shoppingcart.paymentsapi.repository.PaymentsRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private PaymentsRepository paymentRepository;

    @Override
    public Payment payOrder(PaymentDTO paymentDTO, String username) {
        if(paymentRepository.findByOrderId(paymentDTO.getOrderId()).isPresent()){
            throw new OrderAlreadyPaidException("Order already paid");
        } else {
            ResponseEntity<OrderDTO> response = orderClient.viewOrderDetails(paymentDTO.getOrderId(), username);
            OrderDTO orderDTO = response.getBody();
            if (orderDTO == null) {
                throw new OrderNotFoundException("Order not found");
            }
            try {
                if (orderDTO.getStatus().equalsIgnoreCase("COMPLETED")) {
                    throw new OrderAlreadyCompletedException("Order already completed");
                }
                if (orderDTO.getStatus().equalsIgnoreCase("CANCELLED")) {
                    throw new IllegalArgumentException("Cannot pay a cancelled order");
                }
                double amountToPay;
                if (paymentDTO.getPaymentMethod() == PaymentMehod.CARD) {
                    if (paymentDTO.getCardInfo() == null) {
                        throw new CardInfoRequiredException("Card info is required for CARD payment");
                    }
                    amountToPay = orderDTO.getTotalPrice();
                    System.out.println("Processing " + paymentDTO.getPaymentMethod() + " payment: " + paymentDTO.getCardInfo().getCardNumber());
                } else {
                    paymentDTO.setCardInfo(null);
                    amountToPay = paymentDTO.getAmount();
                    System.out.println("Processing " + paymentDTO.getPaymentMethod() + " payment");
                }
                if (paymentDTO.getAmount() < orderDTO.getTotalPrice()) {
                    throw new InsufficientAmountException("Insufficient amount. Order total is " + orderDTO.getTotalPrice());
                }
                orderClient.completeOrder(paymentDTO.getOrderId(), username);
                Payment payment = new Payment();
                payment.setOrderId(paymentDTO.getOrderId());
                payment.setAmount(amountToPay);
                payment.setPaymentMethod(paymentDTO.getPaymentMethod());
                payment.setPaymentDate(LocalDateTime.now());
                double change = 0.00;
                if (paymentDTO.getPaymentMethod() == PaymentMehod.CASH){
                    change = paymentDTO.getAmount() - orderDTO.getTotalPrice();
                    change = Math.round(change * 100.0) / 100.0;
                    if (change < 0) {
                        change = 0.00;
                    }
                }
                payment.setChange(change);
                paymentRepository.save(payment);
                return payment;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId, String username) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment for order " + orderId + " not found"));
        ResponseEntity<OrderDTO> response = orderClient.viewOrderDetails(orderId, username);
        OrderDTO orderDTO = response.getBody();

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setChange(payment.getChange());
        paymentResponse.setPaymentMethod(payment.getPaymentMethod());
        paymentResponse.setPaymentDate(payment.getPaymentDate());
        paymentResponse.setOrder(orderDTO);
        return paymentResponse;
    }
}
