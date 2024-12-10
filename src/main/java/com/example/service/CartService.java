package com.example.service;

import com.example.dao.CartDAO;
import com.example.model.Cart;
import com.example.model.CartItem;

import java.sql.SQLException;
import java.util.List;

public class CartService {
    private final CartDAO cartDAO = new CartDAO();

    // Lấy giỏ hàng của người dùng
    public Cart getCartByUserId(int userId) throws SQLException {
        return cartDAO.getCartByUserId(userId);
    }

    // Tạo giỏ hàng mới cho người dùng
    public int createCart(int userId) throws SQLException {
        return cartDAO.createCart(userId);
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<CartItem> getCartItemsByCartId(int cartId) throws SQLException {
        return cartDAO.getCartItemsByCartId(cartId);
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addItemToCart(CartItem cartItem) throws SQLException {
        cartDAO.addItemToCart(cartItem);
    }

    // Cập nhật số lượng sản phẩm trong giỏ
    public void updateCartItemQuantity(int cartItemId, int quantity) throws SQLException {
        cartDAO.updateCartItemQuantity(cartItemId, quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItemFromCart(int cartItemId) throws SQLException {
        cartDAO.removeItemFromCart(cartItemId);
    }

    // Cập nhật giỏ hàng
    public void updateCart(Cart cart) throws SQLException {
        cartDAO.updateCart(cart);
    }

    // Kiểm tra xem giỏ hàng có tồn tại cho người dùng không
    public boolean doesCartExist(int userId) throws SQLException {
        return cartDAO.doesCartExist(userId);
    }
    
    public void clearCart(int cartId) throws SQLException {
        cartDAO.clearCart(cartId); // Gọi phương thức của DAO để xóa các item trong giỏ hàng
    }
}
