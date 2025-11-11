<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register for Reader Card - Library System</title>

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

        .card {
            border-radius: 20px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
            background-color: white;
        }

        .form-label {
            font-weight: 600;
        }

        .btn-submit {
            background-color: #2563eb;
            color: #fff;
            font-weight: 600;
            border-radius: 50px;
            padding: 12px 30px;
            transition: 0.3s;
        }

        .btn-submit:hover {
            background-color: #1e40af;
            transform: scale(1.05);
        }

        .navbar {
            background-color: #1f2937 !important;
        }

        .navbar-brand, .nav-link {
            color: #fff !important;
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

<!-- Form Section -->
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card p-5">
                <h2 class="text-center text-primary fw-bold mb-4">📖 Register for Reader Card</h2>
                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% if (errorMessage != null) { %>
                <div class="alert alert-danger text-center fw-bold"><%= errorMessage %>
                </div>
                <% } %>

                <form action="ReaderCard" method="post">
                    <h5 class="mb-3 text-secondary fw-bold">Personal Information</h5>

                    <div class="mb-3">
                        <label for="fullName" class="form-label">Full Name *</label>
                        <input type="text" id="fullName" name="fullName" class="form-control"
                               placeholder="Enter your full name" required>
                    </div>

                    <div class="mb-3">
                        <label for="dateOfBirth" class="form-label">Date of Birth *</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email *</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="example@gmail.com"
                               required>
                    </div>

                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number *</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" class="form-control"
                               placeholder="0123456789" required>
                    </div>

                    <hr>
                    <h5 class="mb-3 text-secondary fw-bold">Address Information</h5>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="houseNumber" class="form-label">House Number *</label>
                            <input type="text" id="houseNumber" name="houseNumber" class="form-control"
                                   placeholder="123A">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="street" class="form-label">Street *</label>
                            <input type="text" id="street" name="street" class="form-control" placeholder="Le Loi">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="ward" class="form-label">Ward *</label>
                            <input type="text" id="ward" name="ward" class="form-control" placeholder="Ward 5">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="city" class="form-label">City *</label>
                            <input type="text" id="city" name="city" class="form-control"
                                   placeholder="Ho Chi Minh City">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="country" class="form-label">Country *</label>
                        <input type="text" id="country" name="country" class="form-control" placeholder="Vietnam">
                    </div>

                    <div class="text-center mt-4">
                        <button type="submit" class="btn btn-submit">Register</button>
                        <a href="ReaderMainView.jsp" class="btn btn-outline-secondary ms-3">Back</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
