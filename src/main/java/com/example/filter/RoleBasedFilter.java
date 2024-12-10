package com.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class RoleBasedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        if (req.getRequestURI().contains("/admin")) {
            chain.doFilter(request, response);
            return;
        }
        
        if (req.getRequestURI().contains("/user")) {
            chain.doFilter(request, response);
            return;
        }
        
        if (req.getRequestURI().contains("/vendor")) {
            chain.doFilter(request, response);
            return;
        }

        // Nếu người dùng đã đăng nhập và không yêu cầu /home
        if (session != null && session.getAttribute("role") != null) {
            String role = (String) session.getAttribute("role");
            String pageContent = "/views/home.jsp"; // Trang mặc định cho người dùng
            String layout = "/layouts/user_layout.jsp"; // Layout mặc định cho người dùng

            // Xử lý layout tùy theo vai trò
            switch (role) {
                case "ADMIN":
                    layout = "/layouts/admin_layout.jsp";  // Layout cho quản trị viên
                    pageContent = "/admin/home.jsp"; // Trang cho admin
                    break;
                case "VENDOR":
                    layout = "/layouts/admin_layout.jsp"; // Layout cho vendor
                    pageContent = "/vendor/home.jsp"; // Trang cho vendor
                    break;
                case "SHIPPER":
                    layout = "/layouts/admin_layout.jsp"; // Layout cho shipper
                    pageContent = "/shipper/home.jsp"; // Trang cho shipper
                    break;
                case "USER":
                default:
                    layout = "/layouts/user_layout.jsp"; // Layout cho user
                    pageContent = "/views/home.jsp"; // Trang cho user
                    break;
            }

            // Gửi về layout và trang tương ứng với vai trò
            req.setAttribute("pageContent", pageContent);
            req.getRequestDispatcher(layout).forward(req, res);
        } else {
            if (req.getRequestURI().contains("/home") || req.getRequestURI().contains("/login") || req.getRequestURI().contains("/register")) {
                req.setAttribute("pageContent", "/views/home.jsp");
                req.getRequestDispatcher("/layouts/user_layout.jsp").forward(req, res);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }

    @Override
    public void destroy() {}
}
