package com.ecommerce.dexry.repository;

import com.ecommerce.dexry.model.Order;
import com.ecommerce.dexry.model.User;
import com.ecommerce.dexry.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);
}
