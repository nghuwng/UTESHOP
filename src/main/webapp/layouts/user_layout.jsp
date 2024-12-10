<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }

        .content {
            min-height: calc(100vh - 60px - 60px);
        }

        footer {
            height: 60px; /* Ví dụ footer có chiều cao 60px */
        }
    </style>
</head>
<body>
    <!-- Header -->
    <jsp:include page="/partials/header.jsp" />

    <!-- Content -->
    <div class="container mt-4 content">
        <jsp:include page="${pageContent}" />
    </div>

    <!-- Footer -->
    <jsp:include page="/partials/footer.jsp" />
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
