package com.ecommerce.dexry.controller;

import com.ecommerce.dexry.model.Product;
import com.ecommerce.dexry.model.Category;
import com.ecommerce.dexry.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/categories")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            return ResponseEntity.ok(adminService.addCategory(category));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            return ResponseEntity.ok(adminService.updateCategory(id, category));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return adminService.deleteCategory(id) ?
                ResponseEntity.ok("Category deleted successfully") :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(adminService.getAllCategories());
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestParam Long categoryId){
              try{
                  Product savedProduct = adminService.addProduct(product, categoryId);
                  return ResponseEntity.ok(savedProduct);
              } catch (RuntimeException e){
                  return ResponseEntity.badRequest().body(e.getMessage());
              }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product, @RequestParam(required = false) Long categoryId){
        try{
            Product updatedProduct = adminService.updateProduct(id, product, categoryId);
            return ResponseEntity.ok(updatedProduct);
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        boolean deleted = adminService.deleteProduct(id);
        if(deleted){
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(adminService.getAllProducts());
    }

}
