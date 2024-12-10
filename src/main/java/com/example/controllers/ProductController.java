package com.example.controllers;

import com.example.model.Category;
import com.example.model.Product;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.productService = new ProductService();
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("listByCategory".equals(action)) {
                // Lấy danh sách sản phẩm theo danh mục
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));

                Category category = categoryService.getCategoryById(categoryId);
                if (category == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found.");
                    return;
                }

                List<Product> products = productService.getProductsByCategoryId(categoryId);
                request.setAttribute("category", category);
                request.setAttribute("products", products);
            } else {
                // Lấy tất cả sản phẩm nếu không có action hoặc action không hợp lệ
                List<Product> products = productService.getAllProducts();
                
                Category category = new Category("All");
                request.setAttribute("category", category);
                request.setAttribute("products", products);
            }
            
         // Lấy danh mục cho header
            List<Category> categories = categoryService.getAllCategories();
            request.setAttribute("categories", categories);

            request.setAttribute("pageContent", "/views/listByCategory.jsp");
            request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching products.");
        }
    }
}

