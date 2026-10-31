package com.example.order_management.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(

        @NotBlank(message = "Customer name is required")
        String customerName,

        @NotEmpty(message = "An order must contain at least one item")
        List<@Valid OrderItemRequest> items
) {
}