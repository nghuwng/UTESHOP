<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ</title>
    <style>
        /* Custom styles for product list */
        .product-list {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }

        .product-item {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 15px;
            text-align: center;
            width: 200px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .product-item:hover {
            transform: translateY(-10px); /* Hover effect */
        }

        .product-item img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 10px;
        }

        .product-name {
            font-size: 18px;
            font-weight: bold;
            margin: 10px 0;
            color: #333;
        }

        .product-price {
            color: #e91e63;
            font-size: 16px;
            margin: 5px 0;
        }

        .product-description {
            font-size: 14px;
            color: #555;
            margin-bottom: 10px;
            height: 40px;
            overflow: hidden;
        }

        .btn-buy {
            background-color: #007bff;
            color: #fff;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-buy:hover {
            background-color: #0056b3;
        }

        .product-item a {
            text-decoration: none;
            color: #fff;
        }

        .product-item a:hover {
            color: #ddd;
        }

        /* Slider styling */
        .carousel-item img {
            width: 100%;
            height: 400px;
            object-fit: cover;
            border-radius: 10px;
        }

        .carousel-caption h5 {
            font-size: 24px;
            font-weight: bold;
            color: #fff;
        }

        .carousel-caption p {
            font-size: 16px;
            color: #fff;
        }
    </style>
</head>
<body>

    <!-- Slider for Images (Ảnh có sẵn từ server) -->
    <div id="imageCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <!-- Thêm các ảnh vào slider từ server -->
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/images/slide1.webp" alt="Image 1">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/images/slide2.webp" alt="Image 2">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/images/slide3.webp" alt="Image 3">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#imageCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#imageCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <div class="container mt-5">
        <h2>Sản phẩm nổi bật</h2>

        <div class="product-list">
            <c:forEach var="product" items="${randomProducts}">
                <div class="product-item">
                    <img src="data:image/jpeg;base64,${product.imageBase64}" alt="${product.name}">
                    <div class="product-name">${product.name}</div>
                    <div class="product-price">
                        <fmt:formatNumber value="${product.price}" pattern="#,### đ" />
                    </div>
                    <div class="product-description">${product.description}</div>
                    <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}" class="btn-buy">Xem chi tiết</a>
                </div>
            </c:forEach>
        </div>
    </div>

</body>
</html>
