package com.ecommerce.dexry.repository;

import com.ecommerce.dexry.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // We can add custom queries later if needed
}
