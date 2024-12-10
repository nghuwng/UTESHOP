<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đơn hàng</title>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Lịch sử đơn hàng</h1>

        <c:if test="${not empty orders}">
            <table class="table table-bordered mt-4">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Mã đơn hàng</th>
                        <th scope="col">Ngày tạo</th>
                        <th scope="col">Tổng giá</th>
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Chi tiết</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.id}</td>
                            <td><fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                            <td><fmt:formatNumber value="${order.totalPrice}" pattern="#,###,### đ" /></td>
                            <td>${order.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/user/infor/my-order?orderId=${order.id}" class="btn btn-info">Xem chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty orders}">
            <p class="alert alert-info text-center">Bạn chưa có đơn hàng nào.</p>
        </c:if>
    </div>
</body>
</html>
