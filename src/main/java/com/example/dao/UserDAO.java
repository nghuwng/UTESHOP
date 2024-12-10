package com.example.dao;

import com.example.model.User;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    User.Role.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching user by email", e);
        }
        return user;
    }

    public boolean addUser(User newUser) throws SQLException {
        String query = "INSERT INTO users (email, password_hash, name, phone, role) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUser.getEmail());
            stmt.setString(2, newUser.getPasswordHash());
            stmt.setString(3, newUser.getName());
            stmt.setString(4, newUser.getPhone());
            stmt.setString(5, newUser.getRole().toString());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while adding user", e);
        }
    }
    
    public User getUserById(int id) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    User.Role.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching user by ID", e);
        }
        return user;
    }

    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET name = ?, phone = ?, role = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getRole().toString());
            stmt.setInt(4, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating user", e);
        }
    }
    
    public List<User> getUsersWithPaginationAndSearch(int page, int size, String searchTerm) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE name LIKE ? LIMIT ?, ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Thêm dấu "%" vào từ khóa tìm kiếm để hỗ trợ tìm kiếm chứa từ khóa ở bất kỳ đâu trong tên
            stmt.setString(1, "%" + (searchTerm != null ? searchTerm : "") + "%");
            stmt.setInt(2, (page - 1) * size); // Tính offset
            stmt.setInt(3, size);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    User.Role.valueOf(rs.getString("role"))
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching users with pagination and search", e);
        }
        return users;
    }

    public int getTotalUsersCount(String searchTerm) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM users WHERE name LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + (searchTerm != null ? searchTerm : "") + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while counting total users", e);
        }

        return count;
    }
    
    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while deleting user", e);
        }
    }

}
