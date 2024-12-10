package com.example.controllers;

import com.example.model.Address;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.model.Product;
import com.example.service.AddressService;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();
    private final AddressService addressService = new AddressService();
    private final ProductService productService = new ProductService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");

            // Kiểm tra xem người dùng có đăng nhập không
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Lấy giỏ hàng của người dùng
            Cart cart = cartService.getCartByUserId(userId);
            if (cart == null || cart.getCartItems().isEmpty()) {
                request.setAttribute("error", "Giỏ hàng của bạn trống.");
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Tính tổng giá trị giỏ hàng
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CartItem item : cart.getCartItems()) {
                totalPrice = totalPrice.add(BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            // Lấy danh sách địa chỉ giao hàng của người dùng (cần có service để lấy thông tin địa chỉ)
            List<Address> addresses = addressService.getAddressesByUserId(userId);
            
            // Đưa thông tin vào request để hiển thị trên checkout.jsp
            request.setAttribute("cart", cart);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("addresses", addresses);

            // Chuyển hướng đến trang thanh toán
            request.setAttribute("pageContent", "/user/checkout.jsp");
            request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi xảy ra khi tải thông tin thanh toán.");
            request.getRequestDispatcher("/cart").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");

            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Lấy giỏ hàng của người dùng
            Cart cart = cartService.getCartByUserId(userId);
            if (cart == null) {
                request.setAttribute("error", "Giỏ hàng của bạn trống.");
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<CartItem> cartItems = cartService.getCartItemsByCartId(cart.getId());
            if (cartItems.isEmpty()) {
                request.setAttribute("error", "Giỏ hàng của bạn trống.");
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Tính tổng giá trị đơn hàng
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CartItem item : cartItems) {
                totalPrice = totalPrice.add(BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            // Lấy địa chỉ giao hàng từ request (địa chỉ đã chọn)
            int shippingAddressId = Integer.parseInt(request.getParameter("shippingAddressId"));
            
            // lấy id shop:
            Product product = productService.getProductById(cartItems.get(0).getProductId());
            int shopId = product.getShopId();

            // Tạo Order
            Order order = new Order(
                0,
                userId,
                "NEW",
                totalPrice,
                "COD",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
            );
            order.setShippingAddressId(shippingAddressId);  // Set địa chỉ giao hàng vào đơn hàng
            order.setShopId(shopId);

            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                orderItems.add(new OrderItem(
                    0, // ID tự tăng trong CSDL
                    0, // Tạm thời là 0, sẽ được cập nhật sau khi Order được lưu
                    item.getProductId(),
                    item.getQuantity(),
                    BigDecimal.valueOf(item.getPrice()) // Ép kiểu price thành BigDecimal
                ));
            }

            // Lưu Order và OrderItems
            int orderId = orderService.createOrder(order, orderItems);

            // Xóa giỏ hàng sau khi tạo đơn hàng thành công
            cartService.clearCart(cart.getId());

            response.sendRedirect(request.getContextPath() + "/user/infor/my-order");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi xử lý đơn hàng.");
            request.getRequestDispatcher("/cart").forward(request, response);
        }
    }
}
