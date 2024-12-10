<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Not Found</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">404 - Trang không tìm thấy</h1>
        <p>Xin lỗi, trang bạn yêu cầu không tồn tại.</p>
        <a href="${pageContext.request.contextPath}" class="btn btn-primary">Quay lại trang chủ</a>
    </div>
</body>
</html>
