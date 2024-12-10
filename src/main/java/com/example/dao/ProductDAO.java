package com.example.dao;

import com.example.model.Product;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Lấy danh sách sản phẩm theo shop_id
    public List<Product> getProductsByShopId(int shopId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE shop_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shopId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        rs.getInt("shop_id"),
                        rs.getString("image_base64"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching products", e);
        }
        return products;
    }

    // Lấy toàn bộ sản phẩm cho admin
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        rs.getInt("shop_id"),
                        rs.getString("image_base64"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching products", e);
        }
        return products;
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, description, price, stock, category_id, shop_id, image_base64) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setInt(5, product.getCategoryId());
            stmt.setInt(6, product.getShopId());
            stmt.setString(7, product.getImageBase64());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while adding product", e);
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, category_id = ?, shop_id = ?, image_base64 = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setInt(5, product.getCategoryId());
            stmt.setInt(6, product.getShopId());
            stmt.setString(7, product.getImageBase64());
            stmt.setInt(8, product.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating product", e);
        }
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while deleting product", e);
        }
    }
    
    public Product getProductById(int id) throws SQLException {
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id"),
                    rs.getInt("shop_id"),
                    rs.getString("image_base64"),  // Assuming the image is stored as Base64
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching product by ID", e);
        }
        return product;
    }
    
    public List<Product> findRandomProducts(int limit) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY RAND() LIMIT ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id"),
                    rs.getInt("shop_id"),
                    rs.getString("image_base64"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching random products", e);
        }
        return products;
    }

    public List<Product> getProductsByCategoryId(int categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        rs.getInt("shop_id"),
                        rs.getString("image_base64"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching products by category", e);
        }
        return products;
    }


}
