package com.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String status;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int shippingAddressId;
    private Address shippingAddress; 
    private int shopId;
    private List<OrderItem> items; // Danh sách các OrderItem

    public Order() {}

    public Order(int id, int userId, String status, BigDecimal totalPrice, String paymentMethod,
                 Timestamp createdAt, Timestamp updatedAt, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }
    
    

    public Order(int id, int userId, String status, BigDecimal totalPrice, String paymentMethod, Timestamp createdAt,
			Timestamp updatedAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.totalPrice = totalPrice;
		this.paymentMethod = paymentMethod;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
    public Order(int id, int userId, String status, BigDecimal totalPrice, String paymentMethod,
            Timestamp createdAt, Timestamp updatedAt, Address shippingAddress, List<OrderItem> items) {
	   this.id = id;
	   this.userId = userId;
	   this.status = status;
	   this.totalPrice = totalPrice;
	   this.paymentMethod = paymentMethod;
	   this.createdAt = createdAt;
	   this.updatedAt = updatedAt;
	   this.shippingAddress = shippingAddress;
	   this.items = items;
	}

	// Getters and setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
    
    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }
    
    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    

    public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
