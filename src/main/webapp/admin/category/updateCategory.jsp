<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>Update Category</h2>
        <form action="${pageContext.request.contextPath}/admin/category?action=update" method="POST">
            <input type="hidden" name="id" value="${category.id}">
            <div class="mb-3">
                <label for="name" class="form-label">Category Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${category.name}" required>
            </div>
            <div class="mb-3">
                <label for="parentId" class="form-label">Parent Category (Optional)</label>
                <input type="number" class="form-control" id="parentId" name="parentId" value="${category.parentId}">
            </div>
            <button type="submit" class="btn btn-primary">Update Category</button>
        </form>
        <a href="${pageContext.request.contextPath}/admin/category?action=list" class="btn btn-secondary mt-3">Back to Category List</a>
    </div>
</body>
</html>
