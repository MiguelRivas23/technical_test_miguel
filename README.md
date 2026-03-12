# technical_test_miguel
This project is a shopping cart system implemented as microservices, demonstrating Spring Boot, JPA, Feign Clients, REST APIs, JWT Security, and H2 database.

It includes Customer, Security, Product, Order, and Payment APIs, all integrated to simulate a full e-commerce workflow.

Note: The project includes an example variables.env file and a Postman collection for testing all endpoints. These are provided alongside the repository.

Microservices Overview
1 - Customer API (customer-api)

Manages customer information.

Stores customer data in its own database.

Exposes endpoints to retrieve customer details.

2 - Security API (security-api)

Manages authentication and authorization.

Uses JWT tokens for secure requests.

Associates each user with a customerId.

Provides endpoint /me to get current user's customer data.

3 - Product API (product-api)

Consumes https://fakestoreapi.com/products to fetch products.

Exposes endpoints to list products and get product details.

Acts as a source of product information for orders.

4 - Order API (order-api)

Manages orders and order items.

Uses Feign Clients to:

Get customer information from security-api.

Get product details from product-api.

Exposes endpoints to:

Create an order

View order details (including customer & product info)

Cancel an order

Complete an order

Validates order status before cancelling or completing.

Supports .env configuration to define API URLs and other environment variables.

5 - Payment API (payment-api)

Receives and processes payments for orders.

Supports CASH and CARD payment methods.

Uses Feign Client to fetch order details from order-api.

Payment validation rules:

CANCELLED orders -> cannot pay; throws exception.

COMPLETED orders -> cannot pay again; prevents duplicate payments.

CASH payments -> allows overpayment, calculates change.

CARD payments -> enforces exact order total and requires card info.

Stores payments in H2 database.

Exposes endpoints to:

Pay for an order (/payments/payOrder)

Retrieve payment details including order info (/payments/byOrder/{id})

Custom Exceptions implemented:

PaymentNotFoundException -> No payment exists for the given order.

OrderAlreadyPaidException -> Payment already exists for a completed order.

OrderAlreadyCompletedException -> Order is completed; no further payments allowed.

InsufficientAmountException -> Payment amount less than order total.

CardInfoRequiredException -> CARD payment without card info.

PaymentFailedException -> Any unexpected error during payment processing.

Security and JWT

All APIs are secured using JWT (JSON Web Tokens).

A shared library jwt-validator was created to centralize JWT handling:

Token parsing and validation.

Username extraction from the JWT.

Integration with Spring Security filters.

Used in security-api, order-api, and payment-api via JwtAuthenticationFilter.

Ensures that all requests between services and clients are authenticated.

All APIs share a .env-style file named:

variables.env

# Customer API
CUSTOMER-API-URL=http://localhost:8081/customer

# Product API
PRODUCT-API-URL=http://localhost:8082/products

# Order API
ORDER-API-URL=http://localhost:8083/orders

# FakeStore API
API-FAKESTORE=https://fakestoreapi.com

# Key Secret
SECRET-KEY=0Y5kRqaRfKf9Ij5A4oKEKguOH3iWtPrbxUEXQUGLtRg=

How It Works

Customer logs in via security-api and obtains JWT token.

Order API receives the order request:

Fetches customer info from customer-api.

Fetches product info from product-api.

Stores order and order items in database.

Payment API receives the payment request:

Fetches order details from order-api.

Validates order status and amount.

Saves payment in H2 database.

Updates order status to COMPLETED.

Postman can query payment info including order details and customer info