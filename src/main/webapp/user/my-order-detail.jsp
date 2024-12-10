<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <style>
        /* Cố định kích thước hình ảnh sản phẩm */
        .product-image {
            width: 100px; /* Chiều rộng cố định */
            height: 100px; /* Chiều cao cố định */
            object-fit: cover; /* Đảm bảo hình ảnh không bị méo */
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <h2 class="text-center">Chi tiết đơn hàng #${order.id}</h2>

    <div class="row">
        <div class="col-md-6">
            <h4>Thông tin đơn hàng</h4>
            <table class="table">
                <tr>
                    <th>Trạng thái</th>
                    <td>${order.status}</td>
                </tr>
                <tr>
                    <th>Phương thức thanh toán</th>
                    <td>${order.paymentMethod}</td>
                </tr>
                <tr>
                    <th>Ngày tạo</th>
                    <td>
                        <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                    </td>
                </tr>
                <tr>
                    <th>Tổng tiền</th>
                    <td><fmt:formatNumber value="${order.totalPrice}" pattern="#,### đ" /></td>
                </tr>
                <tr>
				    <th>Địa chỉ giao hàng</th>
				    <td>${order.shippingAddress.address}</td>
				</tr>
            </table>
        </div>

        <div class="col-md-6">
            <h4>Sản phẩm trong đơn hàng</h4>
            <table class="table">
                <thead>
                    <tr>
                        <th>Tên sản phẩm</th>
                        <th>Hình ảnh</th>
                        <th>Số lượng</th>
                        <th>Giá</th>
                        <th>Tổng</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>${item.product.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty item.product.imageBase64}">
                                        <img src="data:image/jpeg;base64,${item.product.imageBase64}" alt="Hình ảnh" class="product-image">
                                    </c:when>
                                    <c:otherwise>
                                        <span>Không có hình ảnh</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${item.quantity}</td>
                            <td><fmt:formatNumber value="${item.price}" pattern="#,### đ" /></td>
                            <td><fmt:formatNumber value="${item.price * item.quantity}" pattern="#,### đ" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/user/infor/my-order" class="btn btn-primary">Quay lại danh sách đơn hàng</a>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>
