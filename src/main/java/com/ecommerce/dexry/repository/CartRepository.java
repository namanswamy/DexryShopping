package com.ecommerce.dexry.repository;

import com.ecommerce.dexry.model.Cart;
import com.ecommerce.dexry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
