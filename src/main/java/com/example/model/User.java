package com.example.model;

public class User {

    private int id;
    private String email;
    private String passwordHash;
    private String name;
    private String phone;
    private Role role;

    public User() {}

    public User(int id, String email, String passwordHash, String name, String phone, Role role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
    
    public User(String email, String passwordHash, String name, String phone, Role role) {
		super();
		this.email = email;
		this.passwordHash = passwordHash;
		this.name = name;
		this.phone = phone;
		this.role = role;
	}

	// Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Enum Role
    public enum Role {
        USER, VENDOR, ADMIN, SHIPPER
    }
}
