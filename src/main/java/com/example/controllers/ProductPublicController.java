package com.example.controllers;

import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.model.Category;
import com.example.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/product/detail")
public class ProductPublicController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productIdStr = request.getParameter("id");

            if (productIdStr == null || productIdStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
                return;
            }

            int productId = Integer.parseInt(productIdStr);

            Product product = productService.getProductById(productId);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            
         // Lấy danh mục cho header
            List<Category> categories = categoryService.getAllCategories();
            request.setAttribute("categories", categories);

            request.setAttribute("product", product);
            
            request.setAttribute("pageContent", "/user/product_detail.jsp");
            request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
        }
    }
}
