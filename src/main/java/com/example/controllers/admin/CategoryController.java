package com.example.controllers.admin;

import com.example.model.Category;
import com.example.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/category")
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("list".equals(action)) {
            try {
                List<Category> categories = categoryService.getAllCategories();
                request.setAttribute("categories", categories);
                request.setAttribute("pageContent", "/admin/category/listCategories.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching categories.");
            }
        } else if ("add".equals(action)) {
            request.setAttribute("pageContent", "/admin/category/addCategory.jsp");
            request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Category category = categoryService.getCategoryById(id);
                request.setAttribute("category", category);
                request.setAttribute("pageContent", "/admin/category/updateCategory.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching category.");
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                categoryService.deleteCategory(id);
                response.sendRedirect(request.getContextPath() + "/admin/category?action=list");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting category.");
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
                String parentId = request.getParameter("parentId");

                Category category = new Category(0, name, parentId.isEmpty() ? null : Integer.parseInt(parentId), null, null);
                categoryService.addCategory(category);

                response.sendRedirect(request.getContextPath() + "/admin/category?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String parentId = request.getParameter("parentId");

                Category category = new Category(id, name, parentId.isEmpty() ? null : Integer.parseInt(parentId), null, null);
                categoryService.updateCategory(category);

                response.sendRedirect(request.getContextPath() + "/admin/category?action=list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }
}
