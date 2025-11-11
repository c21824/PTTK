<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - LibMan Library System</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(to right, #74ebd5, #ACB6E5);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-card {
            background-color: #ffffff;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            width: 400px;
            padding: 40px 35px;
            text-align: center;
        }

        h3 {
            font-weight: 700;
            color: #2563eb;
            margin-bottom: 25px;
        }

        .form-control {
            border-radius: 50px;
            padding: 10px 20px;
        }

        .btn-login {
            border-radius: 50px;
            background-color: #2563eb;
            color: white;
            font-weight: 600;
            padding: 10px 30px;
            transition: 0.3s;
        }

        .btn-login:hover {
            background-color: #1e40af;
            transform: scale(1.05);
        }

        .alert {
            border-radius: 10px;
        }

        footer {
            position: fixed;
            bottom: 15px;
            width: 100%;
            text-align: center;
            font-size: 14px;
            color: white;
        }
    </style>
</head>
<body>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<div class="alert alert-danger text-center position-absolute top-0 start-50 translate-middle-x mt-3 w-50 fw-bold">
    <%= errorMessage %>
</div>
<%
    }
%>

<div class="login-card">
    <h3>🔐Library Member Login</h3>

    <form action="login" method="post">
        <div class="mb-3">
            <input type="text" name="username" class="form-control" placeholder="username" required>
        </div>
        <div class="mb-3">
            <input type="password" name="password" class="form-control" placeholder="password" required>
        </div>
        <button type="submit" class="btn btn-login w-100">Login</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
