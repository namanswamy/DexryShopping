package com.ecommerce.dexry.repository;

import com.ecommerce.dexry.model.CartItem;
import com.ecommerce.dexry.model.User;
import com.ecommerce.dexry.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartUser(User user);

    Optional<CartItem> findByCartUserAndProduct(User user, Product product);

    void deleteByCartUserAndProduct(User user, Product product);
}
