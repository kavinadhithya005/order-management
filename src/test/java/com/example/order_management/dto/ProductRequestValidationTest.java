package com.example.order_management.dto;

import java.math.BigDecimal;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRequestValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void cleanUp() {
        factory.close();
    }

    @Test
    void acceptsValidProductDetails() {
        ProductRequest request =
                new ProductRequest("Mouse", new BigDecimal("799.00"), 15);

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void rejectsInvalidProductDetails() {
        ProductRequest request =
                new ProductRequest("", new BigDecimal("0.00"), -1);

        assertFalse(validator.validate(request).isEmpty());
    }
}