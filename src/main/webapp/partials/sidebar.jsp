<div class="bg-dark text-white border-end" style="width: 250px; min-height: 100vh;">
    <div class="p-4">
        <h5 class="mb-4">Menu</h5>
        <ul class="list-unstyled">
            <% 
                // Kiểm tra role người dùng từ session
                String role = (String) session.getAttribute("role");
                if (role == null) { 
            %>
                <!-- Menu cho Guest (Chưa đăng nhập) -->
                <li><a href="${pageContext.request.contextPath}/home.jsp" class="menu-item">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/products.jsp" class="menu-item">Products</a></li>
            <% 
                } else if (role.equals("USER")) { 
            %>
                <!-- Menu cho User -->
                <li><a href="${pageContext.request.contextPath}/user/home.jsp" class="menu-item">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/user/products.jsp" class="menu-item">Products</a></li>
                <li><a href="${pageContext.request.contextPath}/user/profile.jsp" class="menu-item">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/user/orders.jsp" class="menu-item">Order History</a></li>
                <li><a href="${pageContext.request.contextPath}/user/cart.jsp" class="menu-item">Cart</a></li>
            <% 
                } else if (role.equals("VENDOR")) { 
            %>
                <!-- Menu cho Vendor (Seller) -->
                <li><a href="${pageContext.request.contextPath}/vendor/dashboard.jsp" class="menu-item">Shop Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/vendor/product?action=list" class="menu-item">Manage Products</a></li>
                <li><a href="${pageContext.request.contextPath}/vendor/manage-order?action=list" class="menu-item">Manage Orders</a></li>
                <li><a href="${pageContext.request.contextPath}/vendor/promotions.jsp" class="menu-item">Promotions</a></li>
                <li><a href="${pageContext.request.contextPath}/vendor/revenue?action=list" class="menu-item">Shop Revenue</a></li>
            <% 
                } else if (role.equals("ADMIN")) { 
            %>
                <!-- Menu cho Admin -->
                <li><a href="${pageContext.request.contextPath}/admin/manage-user?action=list" class="menu-item">Manage Users</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/products.jsp" class="menu-item">Manage Products</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/category?action=list" class="menu-item">Manage Categories</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/discounts.jsp" class="menu-item">Manage Discounts</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/shippers.jsp" class="menu-item">Manage Shippers</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/promotions.jsp" class="menu-item">Manage Promotions</a></li>
            <% 
                } else if (role.equals("SHIPPER")) { 
            %>
                <!-- Menu cho Shipper -->
                <li><a href="${pageContext.request.contextPath}/shipper/orders.jsp" class="menu-item">Assigned Orders</a></li>
                <li><a href="${pageContext.request.contextPath}/shipper/statistics.jsp" class="menu-item">Statistics</a></li>
            <% 
                } 
            %>
            <li><a href="${pageContext.request.contextPath}/logout" class="text-danger menu-item">Logout</a></li>
        </ul>
    </div>
</div>


<!-- CSS tùy chỉnh cho sidebar -->
<style>
    /* Màu nền tối cho sidebar */
    .bg-dark {
        background-color: #343a40 !important; /* Nền tối cho sidebar */
    }

    /* Màu chữ nhạt cho các mục menu */
    .text-white {
        color: #fff !important;
    }

    /* Các item menu */
    .menu-item {
        display: block;
        padding: 12px 20px;
        margin-bottom: 10px;
        border-radius: 8px;
        color: #fff;
        text-decoration: none;
        background-color: #495057; /* Màu nền nhạt hơn cho các mục menu */
        transition: all 0.3s ease;
    }

    /* Đổi màu nền khi hover */
    .menu-item:hover {
        background-color: #007bff; /* Màu nền khi hover */
        color: #fff;
        box-shadow: 0 0 8px rgba(0, 123, 255, 0.5); /* Tạo bóng cho item khi hover */
    }

    /* Thêm không gian padding cho sidebar */
    .p-4 {
        padding: 1.5rem;
    }

    /* Title menu */
    h5 {
        font-weight: bold;
        font-size: 1.25rem;
        text-transform: uppercase;
        margin-bottom: 20px;
    }

    /* Logout item */
    .text-danger {
        color: #dc3545 !important;
    }
</style>
