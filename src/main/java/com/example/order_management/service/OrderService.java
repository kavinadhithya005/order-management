package com.example.order_management.service;

import com.example.order_management.dto.OrderItemRequest;
import com.example.order_management.dto.OrderRequest;
import com.example.order_management.entity.CustomerOrder;
import com.example.order_management.entity.OrderItem;
import com.example.order_management.entity.Product;
import com.example.order_management.exception.InsufficientStockException;
import com.example.order_management.exception.ResourceNotFoundException;
import com.example.order_management.repository.CustomerOrderRepository;
import com.example.order_management.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            CustomerOrderRepository orderRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CustomerOrder placeOrder(OrderRequest request) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomerName(request.customerName());

        BigDecimal orderTotal = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found: " + itemRequest.productId()));

            int quantity = itemRequest.quantity();

            if (product.getStock() < quantity) {
                throw new InsufficientStockException(
                        "Insufficient stock for product " + product.getId()
                                + ". Available: " + product.getStock()
                                + ", requested: " + quantity);
            }

            product.setStock(product.getStock() - quantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(product.getPrice());

            order.addItem(orderItem);

            BigDecimal lineTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(quantity));
            orderTotal = orderTotal.add(lineTotal);
        }

        order.setTotalAmount(orderTotal);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<CustomerOrder> getAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomerOrder getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found: " + id));
    }
}