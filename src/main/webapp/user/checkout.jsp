<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Thanh toán</h1>

        <!-- Hiển thị giỏ hàng -->
        <c:if test="${not empty cart}">
            <table class="table table-bordered mt-4">
                <thead class="table-light">
                    <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Giá</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart.cartItems}">
                        <tr>
                            <td>${item.productId}</td>
                            <td>Product ${item.productId}</td> <!-- Thay bằng tên sản phẩm thực tế -->
                            <td>${item.quantity}</td>
                            <td><fmt:formatNumber value="${item.price}" pattern="#,###,### đ" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Hiển thị tổng giá giỏ hàng -->
            <div class="text-end mt-3">
                <h4>Tổng giá giỏ hàng: 
                    <fmt:formatNumber value="${totalPrice}" pattern="#,###,### đ" />
                </h4>
            </div>
        </c:if>

        <c:if test="${empty cart}">
            <p class="alert alert-info text-center">Giỏ hàng của bạn hiện tại chưa có sản phẩm nào.</p>
        </c:if>

        <!-- Chọn địa chỉ giao hàng -->
        <form action="${pageContext.request.contextPath}/order" method="post">
            <div class="mb-3">
                <label for="shippingAddressId" class="form-label">Chọn địa chỉ giao hàng:</label>
                <select name="shippingAddressId" id="shippingAddressId" class="form-control">
                    <c:forEach var="address" items="${addresses}">
                        <option value="${address.id}">${address.address}</option> <!-- Sử dụng address thay vì fullAddress -->
                    </c:forEach>
                </select>
            </div>

            <!-- Button thanh toán -->
            <div class="text-center">
                <button type="submit" class="btn btn-success">Đặt hàng</button>
            </div>
        </form>

    </div>
</body>
</html>
