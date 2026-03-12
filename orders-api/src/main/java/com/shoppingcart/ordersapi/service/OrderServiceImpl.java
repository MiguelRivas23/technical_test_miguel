package com.shoppingcart.ordersapi.service;

import com.shoppingcart.ordersapi.client.CustomerClient;
import com.shoppingcart.ordersapi.client.ProductClient;
import com.shoppingcart.ordersapi.constants.OrderStatus;
import com.shoppingcart.ordersapi.exception.OrderAlreadyCancelledException;
import com.shoppingcart.ordersapi.exception.OrderAlreadyCompletedException;
import com.shoppingcart.ordersapi.exception.OrderNotFoundException;
import com.shoppingcart.ordersapi.models.Order;
import com.shoppingcart.ordersapi.models.OrderDetails;
import com.shoppingcart.ordersapi.models.dto.*;
import com.shoppingcart.ordersapi.repository.OrderDetailsRepository;
import com.shoppingcart.ordersapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CustomerClient customerClient;

    @Override
    public Order createOrder(OrderRequest orderRequest, String username) {
        double totalPrice = 0;
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        int numItems = 1;

        for (OrderItemRequest item : orderRequest.getItems()) {
            ProductDTO product = productClient.getProductById(item.getProductId());
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(order.getId());
            orderDetails.setProductId(product.getId());
            orderDetails.setNumItem(numItems++);
            orderDetails.setTitle(product.getTitle());
            orderDetails.setPrice(product.getPrice());
            orderDetails.setQuantity(item.getQuantity());
            orderDetailsRepository.save(orderDetails);
            totalPrice += product.getPrice() * item.getQuantity();
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return order;
    }

    public OrderResponse viewOrderDetails(Long id, String username) {
        Order order = orderRepository.findById(id).orElse(null);

        List<OrderDetails> details = orderDetailsRepository.findByOrderId(id);

        List<OrderDetailsDTO> items = details.stream().map(detail -> {

            OrderDetailsDTO dto = new OrderDetailsDTO();
            dto.setNumItem(detail.getNumItem());
            dto.setProductId(detail.getProductId());
            dto.setTitle(detail.getTitle());
            dto.setPrice(detail.getPrice());
            dto.setQuantity(detail.getQuantity());

            return dto;

        }).toList();
        CustomerDTO customer = customerClient.getCustomerById(order.getCustomerId());
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomer(customer);
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus().name());
        response.setItems(items);

        return response;
    }

    public void cancelOrder(Long id, String username) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        switch (order.getStatus()) {
            case PENDING:
                order.setStatus(OrderStatus.CANCELLED);
                orderRepository.save(order);
                break;
            case COMPLETED:
                throw new OrderAlreadyCompletedException("Order already completed. Cannot cancel.");
            case CANCELLED:
                throw new OrderAlreadyCancelledException("Order already cancelled.");
        }
    }

    public void completeOrder(Long id, String username){
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new OrderAlreadyCancelledException("Cannot complete. Order is cancelled.");
        }
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new OrderAlreadyCompletedException("Order already completed.");
        }
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

}
