package com.example.model;

import java.sql.Timestamp;

public class Category {
    private int id;
    private String name;
    private Integer parentId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Category(int id, String name, Integer parentId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    

    public Category(String name, Integer parentId, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.name = name;
		this.parentId = parentId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	public Category(String name) {
		super();
		this.name = name;
	}


	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
}
