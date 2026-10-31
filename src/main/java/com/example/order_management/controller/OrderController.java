package com.example.order_management.controller;

import com.example.order_management.dto.OrderRequest;
import com.example.order_management.entity.CustomerOrder;
import com.example.order_management.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrder placeOrder(
            @Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping
    public List<CustomerOrder> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerOrder getById(@PathVariable Long id) {
        return orderService.getById(id);
    }
}