package com.ecommerce.dexry.repository;

import com.ecommerce.dexry.model.CartItem;
import com.ecommerce.dexry.model.User;
import com.ecommerce.dexry.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);
}
