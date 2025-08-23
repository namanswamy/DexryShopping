package com.ecommerce.dexry.controller;

import com.ecommerce.dexry.model.Order;
import com.ecommerce.dexry.model.User;
import com.ecommerce.dexry.model.enums.OrderStatus;
import com.ecommerce.dexry.service.OrderService;
import com.ecommerce.dexry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get current user from JWT SecurityContext
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Extract username from JWT
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    /**
     * Checkout - place an order from user’s cart.
     */
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout() {
        Order order = orderService.placeOrder(getCurrentUser());
        return ResponseEntity.ok(order);
    }

    /**
     * Get all orders of the logged-in user.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders() {
        return ResponseEntity.ok(orderService.getOrders(getCurrentUser()));
    }

    /**
     * Get order by ID (only if it belongs to logged-in user).
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        List<Order> orders = orderService.getOrders(getCurrentUser());
        return orders.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update order status (for admin use).
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(order);
    }

    /**
     * Get ALL orders (admin endpoint).
     */
    @GetMapping("/admin/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
