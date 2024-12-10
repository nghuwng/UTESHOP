<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa sản phẩm</title>
</head>
<body>
    <div class="container mt-4">
        <h1>Sửa sản phẩm</h1>
        <form action="${pageContext.request.contextPath}/vendor/product?action=update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.id}">
            <div class="mb-3">
                <label for="name" class="form-label">Tên sản phẩm</label>
                <input type="text" class="form-control" id="name" name="name" value="${product.name}" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Mô tả</label>
                <textarea class="form-control" id="description" name="description" rows="3" required>${product.description}</textarea>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Giá</label>
                <input type="number" class="form-control" id="price" name="price" value="${product.price}" step="0.01" required>
            </div>
            <div class="mb-3">
                <label for="stock" class="form-label">Số lượng kho</label>
                <input type="number" class="form-control" id="stock" name="stock" value="${product.stock}" required>
            </div>
            <div class="mb-3">
                <label for="category_id" class="form-label">Danh mục</label>
                <select class="form-select" id="category_id" name="category_id" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}" ${category.id == product.categoryId ? 'selected' : ''}>${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
		        <label for="image_base64" class="form-label">Hình ảnh</label>
		        <input type="file" class="form-control" id="image_base64" name="image_base64" required>
		    </div>
            <button type="submit" class="btn btn-success">Cập nhật sản phẩm</button>
            <a href="${pageContext.request.contextPath}/vendor/product?action=list" class="btn btn-secondary">Quay lại</a>
        </form>
    </div>
</body>
</html>
