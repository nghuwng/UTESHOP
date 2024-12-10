<%@ page import="com.example.model.User" %>
<%@ page import="com.example.model.User.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.model.Address" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Information</title>
</head>
<body>
    <div class="container mt-4">
        <h2>User Information</h2>

        <!-- Hiển thị thông điệp nếu có -->
        <c:if test="${not empty message}">
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
        </c:if>

        <!-- User Details -->
        <div class="card mb-4">
            <div class="card-body">
                <p><strong>Name:</strong> ${user.name}</p>
                <p><strong>Email:</strong> ${user.email}</p>
                <p><strong>Phone:</strong> ${user.phone}</p>
            </div>
        </div>
        
        <!-- Nếu role là USER, hiển thị nút đăng ký shop -->
        <c:choose>
            <c:when test="${role == 'USER'}">
                <div class="mb-4">
                    <a href="${pageContext.request.contextPath}/shop?action=add" class="btn btn-primary">Register Shop</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="mb-4">
                    <p>Role is not USER. You cannot register a shop.</p>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Hiển thị danh sách địa chỉ trong bảng -->
        <h3>Addresses</h3>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Address</th>
                    <th>Default</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${addresses}">
                    <tr>
                        <td>${item.address}</td>
                        <td>
                        	<c:choose>
						        <c:when test="${item.isDefault() == true}">
						            Yes
						        </c:when>
						        <c:otherwise>
						            No
						        </c:otherwise>
						    </c:choose>
                        </td>
                        <td>
                            <!-- Nút sửa địa chỉ -->
                            <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editAddressModal" 
                                    data-id="${item.id}" data-address="${item.address}" data-default="${item.isDefault()}">
                                Edit
                            </button>
                            <!-- Nút xóa địa chỉ -->
                            <form action="${pageContext.request.contextPath}/user/infor" method="post" class="d-inline">
                                <input type="hidden" name="action" value="deleteAddress">
                                <input type="hidden" name="addressId" value="${item.id}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Form để thêm địa chỉ mới (hiển thị trong Modal) -->
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addAddressModal">Add Address</button>

        <!-- Modal Add Address -->
        <div class="modal fade" id="addAddressModal" tabindex="-1" aria-labelledby="addAddressModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addAddressModalLabel">Add New Address</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/user/infor" method="post">
                            <input type="hidden" name="action" value="addAddress">
                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" name="address" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label for="isDefault" class="form-label">Set as Default</label>
                                <input type="checkbox" name="isDefault" class="form-check-input">
                            </div>
                            <button type="submit" class="btn btn-primary">Add Address</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Edit Address -->
		<div class="modal fade" id="editAddressModal" tabindex="-1" aria-labelledby="editAddressModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title" id="editAddressModalLabel">Edit Address</h5>
		                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		            </div>
		            <div class="modal-body">
		                <form action="${pageContext.request.contextPath}/user/infor" method="post">
		                    <input type="hidden" name="action" value="updateAddress">
		                    <input type="hidden" name="addressId" id="editAddressId">
		                    <div class="mb-3">
		                        <label for="editAddress" class="form-label">Address</label>
		                        <input type="text" name="address" id="editAddress" class="form-control" required>
		                    </div>
		                    <div class="mb-3">
		                        <label for="editIsDefault" class="form-label">Set as Default</label>
		                        <!-- Giữ checkbox để chọn isDefault -->
		                        <input type="checkbox" name="isDefault" id="editIsDefault" class="form-check-input" value="true">
		                    </div>
		                    <button type="submit" class="btn btn-success">Update Address</button>
		                </form>
		            </div>
		        </div>
		    </div>
		</div>


        <!-- Form để cập nhật thông tin người dùng -->
        <h3 class="my-6">Update Information</h3>
        <form action="${pageContext.request.contextPath}/user/infor" method="post">
            <input type="hidden" name="action" value="updateUser">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" name="name" class="form-control" value="${user.name}" required>
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone</label>
                <input type="text" name="phone" class="form-control" value="${user.phone}" required>
            </div>
            <button type="submit" class="btn btn-success">Update Information</button>
        </form>
    </div>

    <!-- JavaScript để đổ dữ liệu vào modal sửa -->
    <script>
    var editAddressModal = document.getElementById('editAddressModal');
    editAddressModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var addressId = button.getAttribute('data-id');
        var address = button.getAttribute('data-address');
        var isDefault = button.getAttribute('data-default') === 'true'; // Chuyển đổi từ chuỗi "true"/"false" thành boolean

        // Đổ dữ liệu vào modal
        document.getElementById('editAddressId').value = addressId;
        document.getElementById('editAddress').value = address;
        document.getElementById('editIsDefault').checked = isDefault; // Thiết lập checkbox

        // Đảm bảo khi form được submit, giá trị đúng được gửi
        document.querySelector('form').onsubmit = function () {
            // Nếu checkbox không được tick, gán giá trị là false
            if (!document.getElementById('editIsDefault').checked) {
                document.getElementById('editIsDefault').value = 'false'; // Chắc chắn là chuỗi 'false'
            }
        };
    });

	</script>
</body>
</html>
