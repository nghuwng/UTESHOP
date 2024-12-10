package com.example.controllers.user;

import com.example.model.Address;
import com.example.model.User;
import com.example.service.AddressService;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/infor")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private AddressService addressService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
        this.addressService = new AddressService(); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("role") != null) {
            String role = (String) session.getAttribute("role");
            Integer userId = (Integer) session.getAttribute("userId"); // Sử dụng userId từ session

            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            try {
                User user = userService.getUserById(userId);
                request.setAttribute("user", user);
                
             // Lấy danh sách địa chỉ của người dùng
                List<Address> addresses = addressService.getAddressesByUserId(userId);
                request.setAttribute("addresses", addresses);
                

                if ("VENDOR".equals(role)) {
                    request.setAttribute("showShopRegister", true); // Để hiển thị nút đăng ký shop
                } else {
                    request.setAttribute("showShopRegister", false);
                }

                request.setAttribute("pageContent", "/user/infor.jsp");
                request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching user information.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("role") != null) {
            Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session

            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

         // Xử lý hành động theo tham số action
            String action = request.getParameter("action");

            if ("updateUser".equals(action)) {
                // Cập nhật thông tin người dùng
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");

                try {
                    User user = userService.getUserById(userId);
                    user.setName(name);
                    user.setPhone(phone);

                    if (userService.updateUser(user)) {
                        session.setAttribute("user", user); // Cập nhật thông tin user trong session
                        request.setAttribute("pageContent", "/user/infor.jsp");
                        request.getRequestDispatcher("/layouts/user_layout.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating user.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing update.");
                }
            } else if ("addAddress".equals(action)) {
                // Thêm địa chỉ mới
                String address = request.getParameter("address");
                boolean isDefault = Boolean.parseBoolean(request.getParameter("isDefault"));

                try {
                	Address address2 = new Address(userId, address, isDefault);
                    if (addressService.addAddress(address2)) {
                        response.sendRedirect(request.getContextPath() + "/user/infor");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding address.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding address.");
                }
            } else if ("deleteAddress".equals(action)) {
                // Xóa địa chỉ
                int addressId = Integer.parseInt(request.getParameter("addressId"));

                try {
                    if (addressService.deleteAddress(addressId)) {
                        response.sendRedirect(request.getContextPath() + "/user/infor");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting address.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting address.");
                }
            }
            else if ("updateAddress".equals(action)) {
                // Cập nhật địa chỉ
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                String newAddress = request.getParameter("address");
                boolean isDefault = Boolean.parseBoolean(request.getParameter("isDefault"));
                System.out.println("check isDefaultL " + isDefault);

                try {
                    Address address = addressService.getAddressById(addressId); // Lấy địa chỉ từ DB
                    if (address != null) {
                        address.setAddress(newAddress);
                        address.setDefault(isDefault);

                        if (addressService.updateAddress(address)) {
                            response.sendRedirect(request.getContextPath() + "/user/infor");
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating address.");
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Address not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating address.");
                }
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}

