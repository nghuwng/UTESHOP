package com.example.service;

import com.example.dao.OrderDAO;
import com.example.model.Order;
import com.example.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();

    // Tạo đơn hàng mới
    public int createOrder(Order order, List<OrderItem> orderItems) throws SQLException {
        return orderDAO.createOrder(order, orderItems);
    }

    // Lấy thông tin đơn hàng theo ID
    public Order getOrderById(int orderId) throws SQLException {
        return orderDAO.getOrderById(orderId);
    }

    // Lấy danh sách đơn hàng của người dùng
    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        return orderDAO.getOrdersByUserId(userId);
    }

    // Lấy danh sách các sản phẩm trong đơn hàng
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }

    // Cập nhật trạng thái đơn hàng
    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        return orderDAO.updateOrderStatus(orderId, status);
    }

    // Xóa đơn hàng
    public boolean deleteOrder(int orderId) throws SQLException {
        return orderDAO.deleteOrder(orderId);
    }
    
    public Order getOrderDetail(int orderId) throws SQLException {
        // Gọi phương thức DAO để lấy chi tiết đơn hàng
        return orderDAO.getOrderDetail(orderId);
    }
    
 // Lấy danh sách đơn hàng của shop
    public List<Order> getOrdersByShopId(int shopId) throws SQLException {
        return orderDAO.getOrdersByShopId(shopId);
    }
    
    public List<Order> getOrdersByShopId(int shopId, String startDate, String endDate) throws SQLException {
        return orderDAO.getOrdersByShopId(shopId, startDate, endDate);
    }
}
