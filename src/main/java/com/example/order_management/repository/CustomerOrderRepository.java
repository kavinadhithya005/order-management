package com.example.order_management.repository;

import com.example.order_management.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Long> {
}