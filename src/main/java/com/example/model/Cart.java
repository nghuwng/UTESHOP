package com.example.model;

import java.sql.Timestamp;
import java.util.List;

public class Cart {
    private int id;
    private int userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<CartItem> cartItems; // Danh sách các sản phẩm trong giỏ hàng

    // Constructors
    public Cart() {}

    public Cart(int id, int userId, Timestamp createdAt, Timestamp updatedAt, List<CartItem> cartItems) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cartItems = cartItems;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
