package com.example.controllers.vendor;

import com.example.model.Order;
import com.example.model.Shop;
import com.example.service.OrderService;
import com.example.service.ShopService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/vendor/manage-order")
public class ManageOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private ShopService shopService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.orderService = new OrderService();
        this.shopService = new ShopService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            try {
                // Lấy userId từ session
                Integer userId = (Integer) request.getSession().getAttribute("userId");

                if (userId == null) {
                    // Nếu userId không tồn tại trong session, chuyển hướng tới trang đăng nhập
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }

                // Truy vấn shopId từ bảng shop theo userId
                Shop shop = shopService.getShopByUserId(userId);
                if (shop == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Shop not found for user.");
                    return;
                }

                int shopId = shop.getId(); 
                List<Order> orders = orderService.getOrdersByShopId(shopId); // Lấy đơn hàng của shop
                request.setAttribute("orders", orders);
                request.setAttribute("pageContent", "/vendor/order/manageOrder.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching orders.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateStatus".equals(action)) {
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String newStatus = request.getParameter("status");
                
                // Cập nhật trạng thái đơn hàng
                boolean isUpdated = orderService.updateOrderStatus(orderId, newStatus);
                if (isUpdated) {
                    response.sendRedirect(request.getContextPath() + "/vendor/manage-order?action=list");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating order status.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
            }
        }
    }
}
