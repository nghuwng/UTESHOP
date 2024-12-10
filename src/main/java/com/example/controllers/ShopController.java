package com.example.controllers;

import com.example.model.Shop;
import com.example.service.ShopService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/shop")
public class ShopController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ShopService shopService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.shopService = new ShopService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            try {
                List<Shop> shops = shopService.getAllShops();
                request.setAttribute("shops", shops);
                request.setAttribute("pageContent", "/admin/shop/listShops.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching shops.");
            }
        } else if ("add".equals(action)) {
            request.setAttribute("pageContent", "/user/addShop.jsp");
            request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Shop shop = shopService.getShopById(id);
                request.setAttribute("shop", shop);
                request.setAttribute("pageContent", "/admin/shop/updateShop.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching shop.");
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                shopService.deleteShop(id);
                response.sendRedirect(request.getContextPath() + "/admin/shop?action=list");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting shop.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int ownerId = Integer.parseInt(request.getParameter("ownerId"));

                Shop shop = new Shop(0, name, description, ownerId, null, null);
                shopService.addShop(shop);
                
                request.setAttribute("message", "Bạn vừa đăng ký shop thành công, vui lòng đăng nhập lại.");

                request.setAttribute("pageContent", "/user/infor.jsp");
                request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int ownerId = Integer.parseInt(request.getParameter("ownerId"));

                Shop shop = new Shop(id, name, description, ownerId, null, null);
                shopService.updateShop(shop);

                response.sendRedirect(request.getContextPath() + "/admin/shop?action=list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }
}
