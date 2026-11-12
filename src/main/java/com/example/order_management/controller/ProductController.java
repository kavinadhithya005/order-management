package com.example.order_management.controller;

import com.example.order_management.entity.Product;
import com.example.order_management.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.order_management.dto.ProductRequest;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/api/products")

@Tag(name = "Products", description = "Operations for managing products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a product", description = "Creates a product using the supplied name, price, and stock")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest request) {
        return productService.create(toProduct(request));
    }

    @Operation(summary = "Get a product", description = "Returns one product by its ID")
    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }
    @Operation(summary = "List all products", description = "Returns all products in the catalog")
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @Operation(summary = "Update a product", description = "Updates the name, price, and stock of the product with the given ID")
    @PutMapping("/{id}")
    public Product update(
        @PathVariable Long id,
        @Valid @RequestBody ProductRequest request) {
            return productService.update(id, toProduct(request));
    }

    @Operation(summary = "Delete a product", description = "Deletes the product with the given ID")
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