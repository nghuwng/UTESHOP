<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách người dùng</title>
    <!-- Thêm Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container my-4">
        <!-- Tìm kiếm -->
        <form action="${pageContext.request.contextPath}/admin/manage-user" method="get" class="d-flex mb-3">
		    <input type="text" name="searchTerm" class="form-control form-control-sm me-2" value="${searchTerm}" placeholder="Tìm kiếm theo tên" style="width: 250px;">
		    <input type="hidden" name="action" value="list">
		    <button type="submit" class="btn btn-sm btn-primary">Tìm kiếm</button>
		</form>

        <!-- Bảng danh sách người dùng -->
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Tên</th>
                    <th>Điện thoại</th>
                    <th>Vai trò</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.name}</td>
                        <td>${user.phone}</td>
                        <td>${user.role}</td>
                        <td>
                            <!-- Chỉnh sửa người dùng -->
                            <a href="${pageContext.request.contextPath}/admin/manage-user?action=edit&id=${user.id}" class="btn btn-sm btn-info">Sửa</a>
                            <!-- Xóa người dùng -->
                            <a href="javascript:void(0);" onclick="confirmDelete(${user.id})" class="btn btn-sm btn-danger">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Phân trang -->
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/manage-user?action=list&page=${currentPage - 1}&searchTerm=${searchTerm}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/manage-user?action=list&page=${i}&searchTerm=${searchTerm}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/manage-user?action=list&page=${currentPage + 1}&searchTerm=${searchTerm}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <!-- JavaScript -->
    <script>
        function confirmDelete(userId) {
            if (confirm("Bạn có chắc muốn xóa người dùng này?")) {
                window.location.href = "${pageContext.request.contextPath}/admin/manage-user?action=delete&id=" + userId;
            }
        }
    </script>
</body>
</html>
