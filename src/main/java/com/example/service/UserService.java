package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User login(String email, String passwordHash) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPasswordHash().equals(passwordHash)) {
            return user;
        }
        return null;
    }

    public boolean registerUser(User newUser) throws SQLException {
        if (userDAO.getUserByEmail(newUser.getEmail()) != null) {
            return false;
        }

        return userDAO.addUser(newUser);
    }
    
    public User getUserById(int id) throws SQLException {
        return userDAO.getUserById(id);
    }

    public boolean updateUser(User user) throws SQLException {
        return userDAO.updateUser(user);
    }
    
 // Phương thức phân trang kết hợp tìm kiếm theo tên
    public List<User> getUsersWithPaginationAndSearch(int page, int size, String searchTerm) throws SQLException {
        return userDAO.getUsersWithPaginationAndSearch(page, size, searchTerm);
    }

    // Phương thức lấy tổng số người dùng để tính số trang
    public int getTotalUsersCount(String searchTerm) throws SQLException {
        return userDAO.getTotalUsersCount(searchTerm);
    }
    
    public boolean deleteUser(int userId) throws SQLException {
        return userDAO.deleteUser(userId);
    }
}
