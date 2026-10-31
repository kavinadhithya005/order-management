package com.example.order_management.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.00", inclusive = false,
                message = "Product price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Product stock is required")
        @Min(value = 0, message = "Product stock cannot be negative")
        Integer stock
) {
}