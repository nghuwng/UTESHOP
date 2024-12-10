package com.example.dao;

import com.example.model.Address;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.model.Product;
import com.example.utils.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public int createOrder(Order order, List<OrderItem> orderItems) throws SQLException {
        String insertOrderQuery = "INSERT INTO orders (user_id, status, total_price, payment_method, shipping_address_id, shop_id) VALUES (?, ?, ?, ?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                // Thêm đơn hàng
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setString(2, order.getStatus());
                orderStmt.setBigDecimal(3, order.getTotalPrice());
                orderStmt.setString(4, order.getPaymentMethod());
                orderStmt.setInt(5, order.getShippingAddressId()); // Lưu địa chỉ giao hàng
                orderStmt.setInt(6, order.getShopId()); 
                orderStmt.executeUpdate();

                // Lấy ID của đơn hàng vừa tạo
                ResultSet rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // Thêm các sản phẩm trong đơn hàng
                    try (PreparedStatement orderItemStmt = conn.prepareStatement(insertOrderItemQuery)) {
                        for (OrderItem item : orderItems) {
                            orderItemStmt.setInt(1, orderId);
                            orderItemStmt.setInt(2, item.getProductId());
                            orderItemStmt.setInt(3, item.getQuantity());
                            orderItemStmt.setBigDecimal(4, item.getPrice());
                            orderItemStmt.addBatch();
                        }
                        orderItemStmt.executeBatch();
                    }
                    conn.commit(); // Commit transaction
                    return orderId;
                } else {
                    conn.rollback(); // Rollback nếu không tạo được đơn hàng
                    throw new SQLException("Failed to create order");
                }
            } catch (SQLException e) {
                conn.rollback(); // Rollback nếu có lỗi
                throw e;
            }
        }
    }

    // Lấy thông tin đơn hàng theo ID
    public Order getOrderById(int orderId) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("status"),
                        rs.getBigDecimal("total_price"),
                        rs.getString("payment_method"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        }
        return null; // Không tìm thấy đơn hàng
    }

    // Lấy danh sách đơn hàng của người dùng
    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("status"),
                        rs.getBigDecimal("total_price"),
                        rs.getString("payment_method"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        }
        return orders;
    }

    // Lấy danh sách sản phẩm trong đơn hàng
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("price")
                ));
            }
        }
        return items;
    }

    // Cập nhật trạng thái đơn hàng
    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa đơn hàng
    public boolean deleteOrder(int orderId) throws SQLException {
        String query = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public Order getOrderDetail(int orderId) throws SQLException {
        String orderQuery = "SELECT o.*, a.id AS address_id, a.user_id, a.address, a.is_default, a.created_at AS address_created_at, a.updated_at AS address_updated_at "
                + "FROM orders o "
                + "JOIN addresses a ON o.shipping_address_id = a.id "
                + "WHERE o.id = ?";

        String orderItemQuery = "SELECT oi.*, p.name AS product_name, p.description AS product_description, p.price AS product_price, p.image_base64 FROM order_items oi "
                + "JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";

        Order order = null;
        List<OrderItem> orderItems = new ArrayList<>();
        Address shippingAddress = null;

        try (Connection conn = getConnection()) {
            // Lấy thông tin đơn hàng và địa chỉ giao hàng
            try (PreparedStatement stmt = conn.prepareStatement(orderQuery)) {
                stmt.setInt(1, orderId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Lấy thông tin đơn hàng
                    order = new Order(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("status"),
                            rs.getBigDecimal("total_price"),
                            rs.getString("payment_method"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at"),
                            null,  // Địa chỉ sẽ được gán sau
                            null   // OrderItems sẽ được gán sau
                    );

                    // Lấy thông tin địa chỉ giao hàng
                    shippingAddress = new Address(
                            rs.getInt("address_id"),
                            rs.getInt("user_id"),
                            rs.getString("address"),
                            rs.getBoolean("is_default"),
                            rs.getTimestamp("address_created_at"),
                            rs.getTimestamp("address_updated_at")
                    );
                }
            }

            // Nếu tìm thấy đơn hàng, lấy danh sách các sản phẩm (OrderItems)
            if (order != null) {
                try (PreparedStatement stmt = conn.prepareStatement(orderItemQuery)) {
                    stmt.setInt(1, orderId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Product product = new Product(
                                rs.getInt("product_id"),
                                rs.getString("product_name"),
                                rs.getString("product_description"),
                                rs.getDouble("product_price"),
                                0,  // stock không cần thiết ở đây
                                0,  // category_id không cần thiết ở đây
                                0,  // shop_id không cần thiết ở đây
                                rs.getString("image_base64"),
                                null,  // created_at không cần thiết ở đây
                                null   // updated_at không cần thiết ở đây
                        );
                        OrderItem orderItem = new OrderItem(
                                rs.getInt("id"),
                                rs.getInt("order_id"),
                                rs.getInt("product_id"),
                                rs.getInt("quantity"),
                                rs.getBigDecimal("price")
                        );
                        orderItem.setProduct(product);  // Gán thông tin sản phẩm vào OrderItem
                        orderItems.add(orderItem);
                    }
                }
                order.setItems(orderItems);  // Gán danh sách OrderItems vào Order
                order.setShippingAddress(shippingAddress);  // Gán địa chỉ giao hàng vào Order
            }
        }

        return order;
    }
    
    public List<Order> getOrdersByShopId(int shopId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE shop_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shopId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String status = rs.getString("status");
                    BigDecimal totalPrice = rs.getBigDecimal("total_price");
                    String paymentMethod = rs.getString("payment_method");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    int shippingAddressId = rs.getInt("shipping_address_id");

                    // Khởi tạo đối tượng Order chỉ với các trường từ bảng orders
                    Order order = new Order(
                        orderId, userId, status, totalPrice, paymentMethod,
                        createdAt, updatedAt
                    );
                    order.setShippingAddressId(shippingAddressId); // Chỉ set shippingAddressId

                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching orders", e);
        }
        return orders;
    }
    
    public List<Order> getOrdersByShopId(int shopId, String startDate, String endDate) throws SQLException {
        List<Order> orders = new ArrayList<>();
        
        // Chuyển startDate và endDate thành java.sql.Date để đảm bảo đúng định dạng
        java.sql.Date startDateSql = java.sql.Date.valueOf(startDate);
        LocalDate endDateLocal = LocalDate.parse(endDate).plusDays(1);  // Thêm một ngày vào endDate
        java.sql.Date endDateSql = java.sql.Date.valueOf(endDateLocal);  // Chuyển endDate đã thêm ngày vào thành java.sql.Date

        // Câu lệnh SQL để lấy đơn hàng trong phạm vi ngày đã cung cấp
        String query = "SELECT * FROM orders WHERE shop_id = ? AND created_at BETWEEN ? AND ?";

        // Kết nối với cơ sở dữ liệu
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, shopId);  // Thiết lập tham số shopId
            stmt.setDate(2, startDateSql);  // Thiết lập tham số startDate
            stmt.setDate(3, endDateSql);  // Thiết lập tham số endDate (đã thêm một ngày)

            try (ResultSet rs = stmt.executeQuery()) {  // Thực thi câu lệnh SQL
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String status = rs.getString("status");
                    BigDecimal totalPrice = rs.getBigDecimal("total_price");
                    String paymentMethod = rs.getString("payment_method");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    int shippingAddressId = rs.getInt("shipping_address_id");

                    // Khởi tạo đối tượng Order với các trường từ bảng orders
                    Order order = new Order(
                        orderId, userId, status, totalPrice, paymentMethod,
                        createdAt, updatedAt
                    );
                    order.setShippingAddressId(shippingAddressId);  // Set shippingAddressId nếu cần

                    // Thêm đối tượng Order vào danh sách orders
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching orders", e);  // Ném lại lỗi nếu có
        }
        
        return orders;  // Trả về danh sách đơn hàng
    }


}
