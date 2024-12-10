package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.model.Product;
import com.example.model.Category;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách 10 sản phẩm ngẫu nhiên
            request.setAttribute("randomProducts", productService.getRandomProducts(10));
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu xảy ra lỗi, truyền danh sách rỗng
            request.setAttribute("randomProducts", new ArrayList<Product>());
        }

        try {
            // Lấy danh mục cho header
            List<Category> categories = categoryService.getAllCategories();
            System.out.println("check category: " + categories.size());
            request.setAttribute("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu xảy ra lỗi, truyền danh sách rỗng
            request.setAttribute("categories", new ArrayList<Category>());
        }

        // Chuyển đến trang chủ
        request.setAttribute("pageContent", "/views/home.jsp");
        request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
    }
}
