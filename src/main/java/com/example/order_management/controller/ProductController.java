package com.example.order_management.controller;

import com.example.order_management.entity.Product;
import com.example.order_management.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.order_management.dto.ProductRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest request) {
        return productService.create(toProduct(request));
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public Product update(
        @PathVariable Long id,
        @Valid @RequestBody ProductRequest request) {
            return productService.update(id, toProduct(request));
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    private Product toProduct(ProductRequest request) {
    Product product = new Product();
    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    return product;
}
}