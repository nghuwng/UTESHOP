package com.example.controllers.vendor;

import com.example.model.Order;
import com.example.model.Shop;
import com.example.service.OrderService;
import com.example.service.ShopService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/vendor/revenue")
public class RevenueController extends HttpServlet {
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

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Shop shop = shopService.getShopByUserId(userId);
            if (shop == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Shop not found for user.");
                return;
            }

            int shopId = shop.getId();

            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            if (startDate == null || endDate == null) {
                LocalDate now = LocalDate.now();
                startDate = now.minusDays(7).toString();
                endDate = now.toString();
            }

            if ("list".equals(action)) {
                List<Order> orders = orderService.getOrdersByShopId(shopId, startDate, endDate);
                System.out.println("check size: " + orders.size());
                BigDecimal totalRevenue = BigDecimal.ZERO;
                Map<String, BigDecimal> dailyRevenueMap = new HashMap<>();

                for (Order order : orders) {
                    totalRevenue = totalRevenue.add(order.getTotalPrice());

                    LocalDate orderDate = order.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String orderDateString = orderDate.toString();
                    dailyRevenueMap.put(orderDateString,
                        dailyRevenueMap.getOrDefault(orderDateString, BigDecimal.ZERO).add(order.getTotalPrice()));
                }

                // Truyền các giá trị cần thiết vào request
                request.setAttribute("totalRevenue", totalRevenue);
                request.setAttribute("dailyRevenueMap", dailyRevenueMap);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                request.setAttribute("pageContent", "/vendor/revenue/listRevenue.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } else {
                request.setAttribute("pageContent", "/vendor/revenue/listRevenue.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching revenue data.");
        }
    }
}


