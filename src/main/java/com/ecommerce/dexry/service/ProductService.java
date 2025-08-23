package com.ecommerce.dexry.service;

import com.ecommerce.dexry.model.Category;
import com.ecommerce.dexry.model.Product;
import com.ecommerce.dexry.repository.CategoryRepository;
import com.ecommerce.dexry.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategoryName(String categoryName) {
        Optional<Category> categoryOpt = categoryRepository.findByNameIgnoreCase(categoryName);
        if (categoryOpt.isPresent()) {
            return productRepository.findByCategory(categoryOpt.get());
        } else {
            throw new RuntimeException("Category not found: " + categoryName);
        }
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

}
