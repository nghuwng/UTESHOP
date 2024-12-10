package com.example.controllers;

import com.example.dao.CartDAO;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Category;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.CategoryService;
import com.example.service.ProductService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private final CartService cartService = new CartService();
    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");

            // Kiểm tra xem người dùng có đăng nhập chưa (session không chứa userId)
            if (userId == null) {
                // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
                response.sendRedirect(request.getContextPath() + "/login");
                return; // Dừng lại để tránh tiếp tục xử lý phía sau
            }

            // Lấy tham số action từ URL để xử lý hành động
            String action = request.getParameter("action");

            // Nếu action = add, thực hiện thêm sản phẩm vào giỏ hàng
            if ("add".equals(action)) {
                String productIdStr = request.getParameter("id");
                String quantityStr = request.getParameter("quantity");

                if (productIdStr != null && !productIdStr.isEmpty()) {
                    int productId = Integer.parseInt(productIdStr);
                    int quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 1; // Mặc định số lượng là 1

                    Product product = productService.getProductById(productId);

                    // Kiểm tra xem người dùng đã có giỏ hàng chưa
                    if (!cartService.doesCartExist(userId)) {
                        cartService.createCart(userId); // Nếu chưa có giỏ hàng, tạo giỏ hàng mới
                    }

                    // Lấy giỏ hàng của người dùng
                    Cart cart = cartService.getCartByUserId(userId);
                    CartItem cartItem = new CartItem(0, cart.getId(), productId, quantity, product.getPrice(), null, null); // Cập nhật CartItem
                    cartService.addItemToCart(cartItem);
                }

                // Chuyển hướng về trang giỏ hàng sau khi thêm sản phẩm
                response.sendRedirect(request.getContextPath() + "/cart");
                return;  // Dừng lại để tránh xử lý tiếp theo
            }
            else {
            	// Kiểm tra xem giỏ hàng có tồn tại hay không
                Cart cart = cartService.getCartByUserId(userId);
                if (cart == null) {
                    // Nếu giỏ hàng không tồn tại, có thể trả về thông báo hoặc xử lý khác
                    request.setAttribute("error", "Giỏ hàng của bạn hiện tại không có sản phẩm nào.");
                    request.setAttribute("cart", null);
                    request.setAttribute("cartItems", new ArrayList<>()); // Giỏ hàng trống
                    request.setAttribute("totalPrice", 0);
                } else {
                    // Nếu giỏ hàng tồn tại, lấy các mục trong giỏ hàng và tính tổng giá trị
                    List<CartItem> cartItems = cartService.getCartItemsByCartId(cart.getId());

                    // Tính tổng giá giỏ hàng
                    double totalPrice = 0;
                    for (CartItem item : cartItems) {
                        totalPrice += item.getQuantity() * item.getPrice();
                    }

                    // Cung cấp thông tin cho JSP
                    request.setAttribute("cart", cart);
                    request.setAttribute("cartItems", cartItems);
                    request.setAttribute("totalPrice", totalPrice);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi xảy ra khi lấy giỏ hàng.");
        }
        
		try {
	        List<Category> categories = categoryService.getAllCategories();
	        request.setAttribute("categories", categories);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Hiển thị nội dung giỏ hàng
        request.setAttribute("pageContent", "/user/cart.jsp");
        request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");

            // Kiểm tra nếu người dùng chưa đăng nhập
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return; // Dừng lại để tránh tiếp tục xử lý phía sau
            }

            // Kiểm tra nếu người dùng chưa có giỏ hàng, tạo giỏ hàng mới
            if (!cartService.doesCartExist(userId)) {
                cartService.createCart(userId);
            }

            // Lấy giỏ hàng của người dùng
            Cart cart = cartService.getCartByUserId(userId);

            // Kiểm tra hành động là cập nhật hay xóa
            String action = request.getParameter("action");

            if ("update".equals(action)) {
                // Lấy thông tin cần cập nhật
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                int newQuantity = Integer.parseInt(request.getParameter("quantity"));

                // Cập nhật số lượng sản phẩm trong giỏ hàng
                cartService.updateCartItemQuantity(cartItemId, newQuantity);
            } 
            else if ("delete".equals(action)) {
                // Lấy ID sản phẩm cần xóa
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

                // Xóa sản phẩm khỏi giỏ hàng
                cartService.removeItemFromCart(cartItemId);
            }

            // Sau khi cập nhật hoặc xóa, chuyển hướng về trang giỏ hàng
            response.sendRedirect(request.getContextPath() + "/cart");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi xảy ra khi cập nhật hoặc xóa sản phẩm.");
            request.setAttribute("pageContent", "/user/cart.jsp");
            request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
        }
    }

}
