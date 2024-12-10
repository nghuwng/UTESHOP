package com.example.dao;

import com.example.model.Shop;
import com.example.model.User;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Lấy tất cả các shop
    public List<Shop> getAllShops() throws SQLException {
        List<Shop> shops = new ArrayList<>();
        String query = "SELECT * FROM shops";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Shop shop = new Shop(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("owner_id"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                shops.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching shops", e);
        }
        return shops;
    }

    // Thêm shop mới
    public boolean addShop(Shop newShop) throws SQLException {
        String insertShopQuery = "INSERT INTO shops (name, description, owner_id) VALUES (?, ?, ?)";
        String updateUserRoleQuery = "UPDATE users SET role = ? WHERE id = ?";

        // Sử dụng transaction để thao tác trên 2 bảng
        Connection conn = null;
        PreparedStatement insertShopStmt = null;
        PreparedStatement updateUserRoleStmt = null;
        boolean result = false;

        try {
            // Mở kết nối và bắt đầu transaction
            conn = getConnection();
            conn.setAutoCommit(false);  // Tắt auto commit để thực hiện transaction

            // Thêm shop mới
            insertShopStmt = conn.prepareStatement(insertShopQuery);
            insertShopStmt.setString(1, newShop.getName());
            insertShopStmt.setString(2, newShop.getDescription());
            insertShopStmt.setInt(3, newShop.getOwnerId());

            int rowsAffected = insertShopStmt.executeUpdate();
            if (rowsAffected > 0) {
                // Cập nhật role của user thành VENDOR sau khi thêm shop thành công
                updateUserRoleStmt = conn.prepareStatement(updateUserRoleQuery);
                updateUserRoleStmt.setString(1, User.Role.VENDOR.name());  // Set role là VENDOR
                updateUserRoleStmt.setInt(2, newShop.getOwnerId());

                int updateRows = updateUserRoleStmt.executeUpdate();
                if (updateRows > 0) {
                    // Commit transaction nếu cả hai thao tác thành công
                    conn.commit();
                    result = true;
                } else {
                    // Nếu cập nhật role thất bại, rollback
                    conn.rollback();
                }
            } else {
                // Nếu thêm shop thất bại, rollback
                conn.rollback();
            }
        } catch (SQLException e) {
            // Nếu có lỗi, rollback toàn bộ transaction
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            throw new SQLException("Error while adding shop and updating user role", e);
        } finally {
            // Đảm bảo đóng các tài nguyên
            if (insertShopStmt != null) insertShopStmt.close();
            if (updateUserRoleStmt != null) updateUserRoleStmt.close();
            if (conn != null) {
                conn.setAutoCommit(true); // Đặt lại auto commit về true
                conn.close();
            }
        }

        return result;
    }

    // Cập nhật thông tin shop
    public boolean updateShop(Shop shop) throws SQLException {
        String query = "UPDATE shops SET name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, shop.getName());
            stmt.setString(2, shop.getDescription());
            stmt.setInt(3, shop.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating shop", e);
        }
    }

    // Xóa shop
    public boolean deleteShop(int shopId) throws SQLException {
        String query = "DELETE FROM shops WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shopId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while deleting shop", e);
        }
    }
    
    // Lấy thông tin shop theo ID
    public Shop getShopById(int id) throws SQLException {
        Shop shop = null;
        String query = "SELECT * FROM shops WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                shop = new Shop(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("owner_id"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching shop by ID", e);
        }
        return shop;
    }
    
    public Shop getShopByUserId(int userId) throws SQLException {
        Shop shop = null;
        String query = "SELECT * FROM shops WHERE owner_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                shop = new Shop(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("owner_id"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching shop by userId", e);
        }
        return shop;
    }
}
