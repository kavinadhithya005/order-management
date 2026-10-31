package com.example.order_management.service;

import com.example.order_management.entity.Product;
import com.example.order_management.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.example.order_management.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + id));
    }

    public Product update(Long id, Product input) {
        Product existing = getById(id);

        existing.setName(input.getName());
        existing.setPrice(input.getPrice());
        existing.setStock(input.getStock());

        return productRepository.save(existing);
    }

    public void delete(Long id) {
        Product existing = getById(id);
        productRepository.delete(existing);
    }
}