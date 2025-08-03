package com.ecommerce.dexry.repository;

import com.ecommerce.dexry.model.Category;
import com.ecommerce.dexry.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByNameContainingIgnoreCase(String name);

}
