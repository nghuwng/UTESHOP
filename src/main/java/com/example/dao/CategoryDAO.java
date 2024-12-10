package com.example.dao;

import com.example.model.Category;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Lấy danh sách tất cả danh mục
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("parent_id"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching categories", e);
        }
        return categories;
    }

    // Thêm danh mục mới
    public boolean addCategory(Category newCategory) throws SQLException {
        String query = "INSERT INTO categories (name, parent_id) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newCategory.getName());
            stmt.setObject(2, newCategory.getParentId(), Types.INTEGER);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while adding category", e);
        }
    }

    // Sửa danh mục
    public boolean updateCategory(Category category) throws SQLException {
        String query = "UPDATE categories SET name = ?, parent_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.setObject(2, category.getParentId(), Types.INTEGER);
            stmt.setInt(3, category.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating category", e);
        }
    }

    // Xóa danh mục
    public boolean deleteCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while deleting category", e);
        }
    }
    
    public Category getCategoryById(int id) throws SQLException {
        Category category = null;
        String query = "SELECT * FROM categories WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("parent_id"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching category by ID", e);
        }
        return category;
    }
}
