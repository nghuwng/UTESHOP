<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Shop</title>
</head>
<body>
    <div class="container mt-4">
        <h2>Create Shop</h2>
        <form action="${pageContext.request.contextPath}/shop?action=add" method="POST">
            <div class="mb-3">
                <label for="name" class="form-label">Shop Name</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Shop Description</label>
                <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
            </div>
            <div class="mb-3">
                <label for="ownerId" class="form-label">Owner (Your Account)</label>
                <input type="text" class="form-control" id="ownerId" name="ownerId" value="${user.id}" readonly>
            </div>
            <button type="submit" class="btn btn-primary">Create Shop</button>
        </form>
        <a href="${pageContext.request.contextPath}/user/infor" class="btn btn-secondary mt-3">Back to Dashboard</a>
    </div>
</body>
</html>
