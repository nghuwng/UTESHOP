<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container mt-5">
    <div class="text-center mb-4">
        <h1 class="display-6 fw-bold">Category: ${category.name}</h1>
    </div>
    <div class="row row-cols-2 row-cols-md-4 g-4">
        <c:forEach var="product" items="${products}">
            <div class="col">
                <div class="card h-100 shadow-sm border-0">
                    <c:if test="${not empty product.imageBase64}">
                        <img src="data:image/jpeg;base64,${product.imageBase64}" 
                             class="card-img-top rounded-top" 
                             alt="${product.name}" 
                             style="height: 250px; object-fit: cover; width: 100%;">
                    </c:if>
                    <div class="card-body p-2 text-center">
                        <h6 class="card-title text-truncate mb-1">${product.name}</h6>
                        <p class="card-text text-truncate small text-muted">${product.description}</p>
                        <p class="fw-bold text-success mb-1">$${product.price}</p>
                    </div>
                    <div class="card-footer bg-light text-center p-2">
                        <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}" 
                           class="btn btn-primary btn-sm me-1">Chi tiết</a>
                        <a href="${pageContext.request.contextPath}/cart?action=add&id=${product.id}&quantity=1" 
                           class="btn btn-success btn-sm">Đặt mua</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
