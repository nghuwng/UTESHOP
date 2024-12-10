<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Giỏ hàng của bạn</h1>

        <c:if test="${not empty cart}">
            <table class="table table-bordered mt-4">
                <thead class="table-light">
                    <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart.cartItems}">
                        <tr>
                            <td>${item.productId}</td>
                            <td>Product ${item.productId}</td> <!-- Thay thế bằng tên sản phẩm thực tế -->
                            <td>
                                <!-- Form chỉnh sửa số lượng -->
                                <form action="cart" method="post" class="d-inline">
                                    <input type="hidden" name="cartItemId" value="${item.id}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control w-auto d-inline">
                                    <input type="hidden" name="action" value="update">
                                    <button type="submit" class="btn btn-warning btn-sm">Cập nhật</button>
                                </form>
                            </td>
                            <td><fmt:formatNumber value="${item.price}" pattern="#,###,### đ" /></td>
                            <td>
                                <!-- Form xóa sản phẩm -->
                                <form action="cart" method="post" class="d-inline">
                                    <input type="hidden" name="cartItemId" value="${item.id}">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
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

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Quay lại danh sách sản phẩm</a>
            <a href="${pageContext.request.contextPath}/order" class="btn btn-success">Tiến hành thanh toán</a>
        </div>
    </div>

</body>
</html>
