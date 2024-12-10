<%@ page import="com.example.model.User" %>
<%@ page import="com.example.model.Category" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-info">
    <div class="container">
        <a class="navbar-brand" href="#">UTEShop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/products">Products</a>
                </li>
                <!-- Dropdown danh mục sản phẩm -->
                <c:if test="${not empty categories}">
                    <li class="nav-item dropdown text-white">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="categoryDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Categories
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="categoryDropdown">
                            <c:forEach var="category" items="${categories}">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/products?action=listByCategory&categoryId=${category.id}">${category.name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <ul class="navbar-nav">
                <% 
                    User user = (User) session.getAttribute("user");
                    if (user != null) { 
                %>
                    <!-- Nếu đã đăng nhập -->
                    <li class="nav-item">
                        <!-- Icon giỏ hàng -->
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/cart">
                            <i class="bi bi-cart-fill text-white"></i> Cart
                        </a>
                    </li>
                    <li class="nav-item dropdown text-white">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle"></i> <%= user.getName() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/infor">Profile</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/infor/my-order">Đơn hàng</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        </ul>
                    </li>
                <% 
                    } else { 
                %>
                    <!-- Nếu chưa đăng nhập -->
                    <li class="nav-item text-white"><a class="nav-link text-white" href="${pageContext.request.contextPath}/login">Login</a></li>
                    <li class="nav-item text-white"><a class="nav-link text-white" href="${pageContext.request.contextPath}/register">Register</a></li>
                <% 
                    } 
                %>
            </ul>
        </div>
    </div>
</nav>
