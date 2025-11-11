<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reader Home - Library System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(to right, #74ebd5, #ACB6E5);
            min-height: 100vh;
        }

        .navbar {
            background-color: #1f2937 !important;
        }

        .navbar-brand, .nav-link {
            color: #fff !important;
        }

        .container {
            margin-top: 100px;
        }

        .card {
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }

        .btn-register {
            background-color: #2563eb;
            color: white;
            font-weight: 600;
            transition: 0.3s;
            border-radius: 50px;
            padding: 12px 30px;
        }

        .btn-register:hover {
            background-color: #1e40af;
            transform: scale(1.05);
        }

        footer {
            position: fixed;
            bottom: 10px;
            width: 100%;
            text-align: center;
            color: white;
            font-size: 14px;
        }
    </style>
</head>
<body>
<% String successMessage = (String) request.getAttribute("successMessage"); %>
<% if (successMessage != null) { %>
<div class="alert alert-success text-center fw-bold mt-3"><%= successMessage %>
</div>
<% } %>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand fw-bold" href="ReaderMainView.jsp#">📚 LibMan</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
            </ul>
        </div>
    </div>
</nav>

<!-- Main Section -->
<div class="container text-center">
    <div class="card p-5 bg-white">
        <h1 class="fw-bold text-primary mb-3">Welcome to the Library System</h1>

        <a href="RegisterForReaderCardView.jsp" class="btn btn-register btn-lg">
            📖 Register for Reader Card
        </a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
