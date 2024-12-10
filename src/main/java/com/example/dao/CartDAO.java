package com.example.dao;

import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // Lấy kết nối tới cơ sở dữ liệu
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Lấy giỏ hàng của người dùng, bao gồm các sản phẩm trong giỏ
    public Cart getCartByUserId(int userId) throws SQLException {
        Cart cart = null;
        String query = "SELECT * FROM carts WHERE user_id = ? ORDER BY created_at DESC LIMIT 1";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int cartId = rs.getInt("id");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                // Lấy danh sách các sản phẩm trong giỏ hàng
                List<CartItem> cartItems = getCartItemsByCartId(cartId);

                // Trả về giỏ hàng cùng với các sản phẩm
                cart = new Cart(cartId, userId, createdAt, updatedAt, cartItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching cart by user ID", e);
        }
        return cart;
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<CartItem> getCartItemsByCartId(int cartId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart_items WHERE cart_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                        rs.getInt("id"),
                        rs.getInt("cart_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching cart items", e);
        }
        return cartItems;
    }

    // Tạo giỏ hàng mới cho người dùng
    public int createCart(int userId) throws SQLException {
        String query = "INSERT INTO carts (user_id) VALUES (?)", generatedColumns = "id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // trả về ID của giỏ hàng mới
                }
            }
            return -1; // nếu không tạo được giỏ hàng
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while creating cart", e);
        }
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addItemToCart(CartItem cartItem) throws SQLException {
        String query = "INSERT INTO cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getProductId());
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setDouble(4, cartItem.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while adding item to cart", e);
        }
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public boolean updateCartItemQuantity(int cartItemId, int quantity) throws SQLException {
        String query = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartItemId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating cart item quantity", e);
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public boolean removeItemFromCart(int cartItemId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while removing item from cart", e);
        }
    }

    // Cập nhật giỏ hàng
    public boolean updateCart(Cart cart) throws SQLException {
        String query = "UPDATE carts SET updated_at = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, cart.getUpdatedAt());
            stmt.setInt(2, cart.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating cart", e);
        }
    }

    // Kiểm tra xem giỏ hàng có tồn tại cho người dùng không
    public boolean doesCartExist(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM carts WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while checking if cart exists", e);
        }
    }
    
    public void clearCart(int cartId) throws SQLException {
        // Kết nối đến cơ sở dữ liệu
        try (Connection conn = DBConnection.getConnection()) {
            // Bắt đầu một transaction để đảm bảo tính toàn vẹn dữ liệu
            conn.setAutoCommit(false);

            // 1. Xóa tất cả các CartItem liên quan đến cartId
            String deleteCartItemsSql = "DELETE FROM cart_items WHERE cart_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteCartItemsSql)) {
                stmt.setInt(1, cartId);
                stmt.executeUpdate(); // Xóa các mục trong giỏ hàng
            }

            // 2. Xóa giỏ hàng (cart) khỏi bảng cart
            String deleteCartSql = "DELETE FROM carts WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteCartSql)) {
                stmt.setInt(1, cartId);
                stmt.executeUpdate(); // Xóa giỏ hàng khỏi bảng cart
            }

            // Commit transaction để lưu lại thay đổi
            conn.commit();
        } catch (SQLException e) {
            // Nếu có lỗi, rollback lại giao dịch
            e.printStackTrace();
            throw new SQLException("Error while clearing cart: " + e.getMessage());
        }
    }
}
