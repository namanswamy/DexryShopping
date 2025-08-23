package com.ecommerce.dexry.controller;

import com.ecommerce.dexry.model.Cart;
import com.ecommerce.dexry.model.CartItem;
import com.ecommerce.dexry.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Get all items in the current user's cart.
     */
    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        Cart cart = cartService.getCart();   // service figures out the user
        return ResponseEntity.ok(cart.getItems());
    }

    /**
     * Add a product to the cart (default quantity = 1).
     */
    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity) {
        Cart cart = cartService.addToCart(productId, quantity);
        return ResponseEntity.ok(cart);
    }

    /**
     * Update the quantity of a product in the cart.
     */
    @PutMapping("/update/{productId}")
    public ResponseEntity<Cart> updateQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        Cart cart = cartService.updateQuantity(productId, quantity);
        return ResponseEntity.ok(cart);
    }

    /**
     * Remove a product from the cart (set quantity = 0).
     */
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        cartService.updateQuantity(productId, 0);
        return ResponseEntity.ok("Removed from cart");
    }

    /**
     * Clear the current user's entire cart.
     */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared");
    }
}
