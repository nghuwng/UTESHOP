<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Đơn hàng</title>
    <!-- Link tới Bootstrap 5 đã được khai báo ở layout cha -->
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Quản lý Đơn hàng</h2>

        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Tổng giá</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Ngày cập nhật</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>
                        <td><fmt:formatNumber value="${order.totalPrice}" pattern="#,### đ" /></td>
                        <td>
                            <span class="badge 
                                <c:choose>
                                    <c:when test="${order.status == 'NEW'}">bg-primary</c:when>
                                    <c:when test="${order.status == 'CONFIRMED'}">bg-warning</c:when>
                                    <c:when test="${order.status == 'SHIPPING'}">bg-info</c:when>
                                    <c:when test="${order.status == 'DELIVERED'}">bg-success</c:when>
                                    <c:when test="${order.status == 'CANCELED'}">bg-danger</c:when>
                                    <c:when test="${order.status == 'RETURNED'}">bg-secondary</c:when>
                                </c:choose>
                            ">${order.status}</span>
                        </td>
                        <td>${order.createdAt}</td>
                        <td>${order.updatedAt}</td>
                        <td>
						    <div class="d-flex justify-content-between align-items-center">
						        <!-- Form với combobox và nút Cập nhật -->
						        <form action="${pageContext.request.contextPath}/vendor/manage-order" method="post" class="d-flex align-items-center">
						            <input type="hidden" name="orderId" value="${order.id}">
						            <select name="status" class="form-select w-auto me-2">
						                <option value="NEW" <c:if test="${order.status == 'NEW'}">selected</c:if>>Mới</option>
						                <option value="CONFIRMED" <c:if test="${order.status == 'CONFIRMED'}">selected</c:if>>Xác nhận</option>
						                <option value="SHIPPING" <c:if test="${order.status == 'SHIPPING'}">selected</c:if>>Đang giao</option>
						                <option value="DELIVERED" <c:if test="${order.status == 'DELIVERED'}">selected</c:if>>Đã giao</option>
						                <option value="CANCELED" <c:if test="${order.status == 'CANCELED'}">selected</c:if>>Hủy</option>
						                <option value="RETURNED" <c:if test="${order.status == 'RETURNED'}">selected</c:if>>Đã trả lại</option>
						            </select>
						            <button type="submit" name="action" value="updateStatus" class="btn btn-sm btn-primary">Cập nhật</button>
						        </form>
						
						        <!-- Nút Xem chi tiết -->
						        <a href="${pageContext.request.contextPath}/vendor/order-details?id=${order.id}" class="btn btn-sm btn-info">Xem chi tiết</a>
						    </div>
						</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
