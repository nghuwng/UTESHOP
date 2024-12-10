package com.example.service;

import com.example.dao.CategoryDAO;
import com.example.model.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService() {
        categoryDAO = new CategoryDAO();
    }

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    // Thêm danh mục
    public boolean addCategory(Category newCategory) throws SQLException {
        return categoryDAO.addCategory(newCategory);
    }

    // Sửa danh mục
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDAO.updateCategory(category);
    }

    // Xóa danh mục
    public boolean deleteCategory(int categoryId) throws SQLException {
        return categoryDAO.deleteCategory(categoryId);
    }
    
    public Category getCategoryById(int id) throws SQLException {
        return categoryDAO.getCategoryById(id);
    }
}
