package com.example.order_management.service;

import com.example.order_management.dto.OrderItemRequest;
import com.example.order_management.dto.OrderRequest;
import com.example.order_management.entity.CustomerOrder;
import com.example.order_management.entity.Product;
import com.example.order_management.exception.InsufficientStockException;
import com.example.order_management.repository.CustomerOrderRepository;
import com.example.order_management.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerOrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void placesOrderCalculatesTotalAndReducesStock() {
        Product mouse = new Product();
        //mouse.setId(3L);
        mouse.setPrice(new BigDecimal("799.00"));
        mouse.setStock(15);

        when(productRepository.findById(3L)).thenReturn(Optional.of(mouse));
        when(orderRepository.save(any(CustomerOrder.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderRequest request = new OrderRequest(
                "Kavin",
                List.of(new OrderItemRequest(3L, 2)));

        CustomerOrder savedOrder = orderService.placeOrder(request);

        assertEquals(13, mouse.getStock());
        assertEquals(new BigDecimal("1598.00"), savedOrder.getTotalAmount());
        verify(orderRepository).save(savedOrder);
    }

    @Test
    void rejectsOrderWhenStockIsInsufficient() {
        Product mouse = new Product();
        //mouse.setId(3L);
        mouse.setPrice(new BigDecimal("799.00"));
        mouse.setStock(1);

        when(productRepository.findById(3L)).thenReturn(Optional.of(mouse));

        OrderRequest request = new OrderRequest(
                "Kavin",
                List.of(new OrderItemRequest(3L, 2)));

        assertThrows(
                InsufficientStockException.class,
                () -> orderService.placeOrder(request));

        assertEquals(1, mouse.getStock());
        verify(orderRepository, never()).save(any(CustomerOrder.class));
    }
}