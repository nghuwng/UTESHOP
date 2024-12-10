<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f7f7f7;
        }
        .register-container {
            max-width: 400px;
            margin: 50px auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            min-width: 400px;
        }
        .error-message {
            color: red;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h3 class="text-center mb-4">Đăng Ký</h3>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Tên</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Số điện thoại</label>
                <input type="text" class="form-control" id="phone" name="phone" required>
            </div>
            <div class="mb-3">
                <label for="role" class="form-label">Vai trò</label>
                <select class="form-select" id="role" name="role" required>
                    <option value="USER">Người dùng</option>
                    <option value="SHIPPER">Nhân viên giao hàng</option>
                </select>
            </div>
            <div class="mb-3">
                <button type="submit" class="btn btn-primary w-100">Đăng Ký</button>
            </div>
            <c:if test="${not empty error}">
                <div class="error-message text-center">
                    <p>${error}</p>
                </div>
            </c:if>
            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập ngay</a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
