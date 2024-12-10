package com.example.controllers.admin;

import com.example.model.User;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/manage-user")
public class ManageUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            handleListUsers(request, response);
        } else if ("edit".equals(action)) {
            handleEditUser(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
        }
    }

    private void handleListUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        String pageParam = request.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int pageSize = 3;

        try {
            // Lấy danh sách người dùng với phân trang và tìm kiếm
            List<User> users = userService.getUsersWithPaginationAndSearch(page, pageSize, searchTerm);
            int totalUsers = userService.getTotalUsersCount(searchTerm);
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            request.setAttribute("users", users);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("searchTerm", searchTerm); // Đưa từ khóa tìm kiếm lên request
            request.setAttribute("pageContent", "/admin/user/listUsers.jsp");
            request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching users.");
        }
    }

    private void handleEditUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                request.setAttribute("user", user);
                request.setAttribute("pageContent", "/admin/user/editUser.jsp");
                request.getRequestDispatcher("/layouts/admin_layout.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching user details.");
        }
    }

    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                // Xóa người dùng (bạn có thể thêm phương thức xóa vào UserService và UserDAO)
                boolean deleted = userService.deleteUser(userId);
                if (deleted) {
                    response.sendRedirect(request.getContextPath() + "/admin/manage-user?action=list");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting user.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting user.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            handleUpdateUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
        }
    }

    private void handleUpdateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                user.setName(name);
                user.setPhone(phone);
                user.setRole(User.Role.valueOf(role.toUpperCase()));

                boolean updated = userService.updateUser(user);
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/manage-user?action=list");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating user.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating user.");
        }
    }
}
