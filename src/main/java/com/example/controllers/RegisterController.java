package com.example.controllers;

import com.example.service.UserService;
import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
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
        request.setAttribute("pageContent", "/views/register.jsp");
        request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        // Validate input
        if (email == null || password == null || name == null || phone == null || role == null) {
            request.setAttribute("error", "All fields are required!");
            
            request.setAttribute("pageContent", "/views/register.jsp");
            request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
            return;
        }

        try {
            String passwordHash = hashPassword(password);

            User.Role userRole;
            try {
                userRole = User.Role.valueOf(role.toUpperCase()); // Chuyển vai trò từ chuỗi sang enum
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid role!");
                
                request.setAttribute("pageContent", "/views/register.jsp");
                request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
                return;
            }

            User newUser = new User(email, passwordHash, name, phone, userRole);

            boolean isRegistered = userService.registerUser(newUser);
            if (isRegistered) {
                
        		request.setAttribute("error", "Register success!");
                
                request.setAttribute("pageContent", "/views/register.jsp");
                request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Email already exists!");
                
                request.setAttribute("pageContent", "/views/register.jsp");
                request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
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
