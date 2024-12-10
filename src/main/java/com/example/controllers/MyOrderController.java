package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.model.Order;
import com.example.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user/infor/my-order")
public class MyOrderController extends HttpServlet {
    private OrderService orderService = new OrderService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy userId từ session
            Integer userId = (Integer) request.getSession().getAttribute("userId");

            // Kiểm tra xem người dùng có đăng nhập không
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

         // Lấy tham số orderId từ query string, kiểm tra nếu có
            String orderIdParam = request.getParameter("orderId");
            if (orderIdParam != null) {
                // Nếu có orderId, lấy thông tin chi tiết đơn hàng
                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderService.getOrderDetail(orderId);
                if (order != null) {
                    request.setAttribute("order", order);
                    request.setAttribute("pageContent", "/user/my-order-detail.jsp");
                    request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("error", "Đơn hàng không tồn tại.");
                }
            } else {
                // Nếu không có orderId, lấy danh sách đơn hàng của người dùng
                List<Order> orders = orderService.getOrdersByUserId(userId);
                request.setAttribute("orders", orders);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi xảy ra khi lấy danh sách đơn hàng.");
        }

        // Chuyển tiếp đến trang hiển thị lịch sử đơn hàng
        request.setAttribute("pageContent", "/user/my-order.jsp");
        request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
    }
}

