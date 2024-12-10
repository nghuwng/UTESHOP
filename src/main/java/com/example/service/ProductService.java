package com.example.service;

import com.example.dao.ProductDAO;
import com.example.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }

    // Lấy danh sách sản phẩm theo shop_id
    public List<Product> getProductsByShopId(int shopId) throws SQLException {
        return productDAO.getProductsByShopId(shopId);
    }

    // Lấy toàn bộ sản phẩm cho admin
    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) throws SQLException {
        return productDAO.addProduct(product);
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) throws SQLException {
        return productDAO.updateProduct(product);
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int productId) throws SQLException {
        return productDAO.deleteProduct(productId);
    }
    
    public Product getProductById(int id) throws SQLException {
        return productDAO.getProductById(id);
    }
    
    public List<Product> getRandomProducts(int limit) throws SQLException {
        return productDAO.findRandomProducts(limit);
    }
    
    public List<Product> getProductsByCategoryId(int categoryId) throws SQLException {
        return productDAO.getProductsByCategoryId(categoryId);
    }

}
