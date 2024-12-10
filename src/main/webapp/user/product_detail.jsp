<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Sản Phẩm</title>
    <style>
        /* Cấu trúc của phần chi tiết sản phẩm */
        .product-detail {
            display: flex;
            align-items: center;  /* Để căn giữa ảnh và thông tin */
            justify-content: space-between;
            margin: 20px;
        }

        .product-image {
            width: 40%;  /* Chiếm 40% chiều rộng cho ảnh */
            padding-right: 20px;
        }

        .product-image img {
            width: 100%;  /* Đảm bảo ảnh không bị lệch */
            height: auto;
        }

        .product-info {
            width: 55%;  /* Chiếm 55% chiều rộng cho thông tin sản phẩm */
        }

        .product-info h3 {
            font-size: 24px;
            font-weight: bold;
        }

        .product-price {
            font-size: 20px;
            color: #d9534f;
            margin-top: 10px;
        }

        .product-description {
            font-size: 16px;
            margin-top: 10px;
        }

        .product-stock, .product-category {
            font-size: 14px;
            margin-top: 5px;
        }

        .product-category {
            font-weight: bold;
        }

        /* Style cho nút "Thêm vào giỏ hàng" */
        .btn-buy {
            display: inline-flex;
            align-items: center;
            background-color: #28a745;  /* Màu xanh cho nút */
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 16px;
            margin-top: 20px;
            transition: background-color 0.3s ease;
        }

        .btn-buy:hover {
            background-color: #218838;  /* Đổi màu khi hover */
        }

        .btn-buy i {
            margin-right: 8px;  /* Khoảng cách giữa icon và text */
        }
    </style>
</head>
<body>
    <h2>Chi Tiết Sản Phẩm</h2>

    <div class="product-detail">
        <div class="product-image">
            <img src="data:image/jpeg;base64,${product.imageBase64}" alt="${product.name}">
        </div>
        <div class="product-info">
            <h3>${product.name}</h3>
            <div class="product-price">
                <fmt:formatNumber value="${product.price}" pattern="#,### đ" />
            </div>
            <div class="product-description">${product.description}</div>
            <div class="product-stock">Số lượng: ${product.stock}</div>
            <div class="product-category">Danh mục: ${product.categoryId}</div>

            <!-- Chỉ gửi id sản phẩm lên server -->
            <a href="${pageContext.request.contextPath}/cart?action=add&id=${product.id}&quantity=1" class="btn-buy">
                <i class="bi bi-cart-plus"></i> Thêm vào giỏ hàng
            </a>
        </div>
    </div>
</body>
</html>
