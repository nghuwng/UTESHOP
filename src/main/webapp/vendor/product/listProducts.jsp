<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>
    <style>
        .product-image {
            width: 100px;
            height: auto;
        }

        /* Giới hạn chiều cao và chiều rộng của cột mô tả */
        .product-description {
            max-width: 300px; /* Giới hạn chiều rộng */
            max-height: 50px; /* Giới hạn chiều cao */
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap; /* Dòng chỉ hiển thị 1 dòng */
        }

        /* Nếu muốn hiển thị nhiều dòng với dấu ... */
        .product-description-multi-line {
            max-width: 300px; /* Giới hạn chiều rộng */
            max-height: 75px; /* Giới hạn chiều cao */
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3; /* Giới hạn 3 dòng */
            -webkit-box-orient: vertical;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1>Danh sách sản phẩm</h1>
        <a href="${pageContext.request.contextPath}/vendor/product?action=add" class="btn btn-primary mb-3">Thêm mới sản phẩm</a>
        
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                    <th>Kho</th>
                    <th>Hình ảnh</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <!-- Áp dụng giới hạn cho mô tả -->
                        <td class="product-description-multi-line">${product.description}</td>
                        <td>${product.price}</td>
                        <td>${product.stock}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty product.imageBase64}">
                                    <img src="data:image/jpeg;base64,${product.imageBase64}" alt="Hình ảnh" class="product-image">
                                </c:when>
                                <c:otherwise>
                                    <span>Không có hình ảnh</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/vendor/product?action=update&id=${product.id}" class="btn btn-warning">Sửa</a>
                            <a href="${pageContext.request.contextPath}/vendor/product?action=delete&id=${product.id}" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
