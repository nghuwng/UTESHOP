package com.example.dao;

import com.example.model.Address;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Lấy danh sách địa chỉ của user
    public List<Address> getAddressesByUserId(int userId) throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Address address = new Address(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("address"),
                        rs.getBoolean("is_default"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                    );
                    addresses.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching addresses", e);
        }
        return addresses;
    }

    // Thêm địa chỉ mới
    public boolean addAddress(Address address) throws SQLException {
        String query = "INSERT INTO addresses (user_id, address, is_default) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, address.getUserId());
            stmt.setString(2, address.getAddress());
            stmt.setBoolean(3, address.isDefault());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while adding address", e);
        }
    }

    // Sửa địa chỉ
    public boolean updateAddress(Address address) throws SQLException {
        String query = "UPDATE addresses SET address = ?, is_default = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address.getAddress());
            stmt.setBoolean(2, address.isDefault());
            stmt.setInt(3, address.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating address", e);
        }
    }

    // Xóa địa chỉ
    public boolean deleteAddress(int addressId) throws SQLException {
        String query = "DELETE FROM addresses WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, addressId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while deleting address", e);
        }
    }
    
    public Address getAddressById(int addressId) throws SQLException {
        Address address = null;
        String query = "SELECT * FROM addresses WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, addressId);  // Gán giá trị của addressId vào câu truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {  // Nếu tìm thấy địa chỉ
                    address = new Address(
                        rs.getInt("id"),           // Lấy id của địa chỉ
                        rs.getInt("user_id"),      // Lấy userId của địa chỉ
                        rs.getString("address"),   // Lấy địa chỉ
                        rs.getBoolean("is_default"), // Kiểm tra nếu địa chỉ là mặc định
                        rs.getTimestamp("created_at"), // Lấy thời gian tạo
                        rs.getTimestamp("updated_at")  // Lấy thời gian cập nhật
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching address by id", e);  // Xử lý ngoại lệ
        }
        return address;  // Trả về địa chỉ nếu tìm thấy, nếu không trả về null
    }
}
