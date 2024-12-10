package com.example.controllers.vendor;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.Shop;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.service.ShopService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@MultipartConfig
@WebServlet("/vendor/product")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private ShopService shopService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.productService = new ProductService();
        this.shopService = new ShopService();
        this.categoryService = new CategoryService();
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
                List<Product> products = productService.getProductsByShopId(shopId);
                request.setAttribute("products", products);
                request.setAttribute("pageContent", "/vendor/product/listProducts.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching products.");
            }
        } else if ("add".equals(action)) {
        	try {
                // Lấy tất cả danh mục
                List<Category> categories = categoryService.getAllCategories();
                request.setAttribute("categories", categories);

                request.setAttribute("pageContent", "/vendor/product/addProduct.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching categories.");
            }
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Product product = productService.getProductById(id);
                List<Category> categories = categoryService.getAllCategories(); 
                
                request.setAttribute("categories", categories);
                request.setAttribute("product", product);
                request.setAttribute("pageContent", "/vendor/product/updateProduct.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching product.");
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                productService.deleteProduct(id);
                response.sendRedirect(request.getContextPath() + "/vendor/product?action=list");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting product.");
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
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                
             // Xử lý hình ảnh từ input type="file"
                Part imagePart = request.getPart("image_base64");
                String imageBase64 = null;
                if (imagePart != null && imagePart.getSize() > 0) {
                    byte[] imageBytes = imagePart.getInputStream().readAllBytes();
                    imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                }

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

                Product product = new Product(0, name, description, price, stock, categoryId, shopId, imageBase64, null, null);
                productService.addProduct(product);

                response.sendRedirect(request.getContextPath() + "/vendor/product?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                
             // Xử lý hình ảnh từ input type="file"
                Part imagePart = request.getPart("image_base64");
                String imageBase64 = null;
                if (imagePart != null && imagePart.getSize() > 0) {
                    byte[] imageBytes = imagePart.getInputStream().readAllBytes();
                    imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                }

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

                Product product = new Product(id, name, description, price, stock, categoryId, shopId, imageBase64, null, null);
                productService.updateProduct(product);

                response.sendRedirect(request.getContextPath() + "/vendor/product?action=list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }
}
