<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doanh thu của Shop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">Doanh thu của Shop</h1>
        
        <!-- Form lọc doanh thu -->
        <div class="mb-4">
            <form action="${pageContext.request.contextPath}/vendor/revenue" method="get">
                <div class="row">
                    <div class="col-md-4">
                        <label for="startDate" class="form-label">Từ ngày:</label>
                        <input type="date" id="startDate" name="startDate" class="form-control" value="${startDate}">
                    </div>
                    <div class="col-md-4">
                        <label for="endDate" class="form-label">Đến ngày:</label>
                        <input type="date" id="endDate" name="endDate" class="form-control" value="${endDate}">
                    </div>
                    <input type="hidden" name="action" value="list" />
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-primary mt-4">Lọc</button>
                    </div>
                </div>
            </form>
        </div>

        <!-- Hiển thị tổng doanh thu -->
        <div class="mb-4">
            <h3>Tổng doanh thu: <span class="text-success"><fmt:formatNumber value="${totalRevenue}" pattern="#,### đ" /></span></h3>
        </div>

        <!-- Hiển thị doanh thu theo ngày -->
        <h4>Doanh thu theo ngày:</h4>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Ngày</th>
                    <th>Doanh thu</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${dailyRevenueMap}">
                    <tr>
                        <td>${entry.key}</td>
                        <td><fmt:formatNumber value="${entry.value}" pattern="#,### đ" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
