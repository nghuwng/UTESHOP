package com.example.controllers;

import com.example.service.UserService;
import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageContent", "/views/login.jsp");
        request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.login(email, hashPassword(password));
            if (user != null) {
                // Tạo session và lưu thông tin người dùng
                HttpSession session = request.getSession();
                session.setAttribute("user", user); // Lưu thông tin user
                session.setAttribute("role", user.getRole().name()); // Lưu role
                session.setAttribute("userId", user.getId()); // Lưu userId vào session

                response.sendRedirect(request.getContextPath() + "/");
            } else {
                // Thông tin đăng nhập không hợp lệ
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
    
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

