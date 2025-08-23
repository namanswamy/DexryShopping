package com.ecommerce.dexry.service;

import com.ecommerce.dexry.model.*;
import com.ecommerce.dexry.model.enums.OrderStatus;
import com.ecommerce.dexry.repository.CartRepository;
import com.ecommerce.dexry.repository.OrderRepository;
import com.ecommerce.dexry.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Place an order for the given user.
     */
    @Transactional
    public Order placeOrder(User user) {
        // ✅ Get user’s cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // ✅ Create new Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // ✅ Convert CartItems → OrderItems
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // check stock
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            // reduce stock
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            // create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

            // attach to order
            order.addItem(orderItem);

            // update total
            totalAmount = totalAmount.add(orderItem.getPrice());
        }

        // ✅ set total amount
        order.setTotalAmount(totalAmount);

        // ✅ save order
        Order savedOrder = orderRepository.save(order);

        // ✅ clear cart after checkout
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    /**
     * Get all orders for a specific user.
     */
    public List<Order> getOrders(User user) {
        return orderRepository.findByUser(user);
    }

    /**
     * Get all orders in the system (for admins).
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Update order status (e.g., PENDING → COMPLETED / CANCELLED).
     */
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}
