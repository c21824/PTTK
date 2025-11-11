<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Libman - Select Statistic Report Type</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #4facfe, #00f2fe);
      min-height: 100vh;
    }

    .navbar {
      background-color: #1e293b !important;
    }

    .navbar-brand, .nav-link {
      color: #fff !important;
      font-weight: 500;
    }

    .nav-link:hover {
      color: #93c5fd !important;
    }

    .dashboard {
      margin-top: 100px;
    }

    .card {
      border-radius: 20px;
      box-shadow: 0 8px 18px rgba(0, 0, 0, 0.1);
      transition: 0.3s;
    }

    .card:hover {
      transform: translateY(-6px);
    }

    .card h3 {
      font-weight: 600;
      color: #1e3a8a;
    }

    .icon {
      font-size: 45px;
      color: #2563eb;
    }

    .btn-report {
      background-color: #2563eb;
      color: white;
      border-radius: 50px;
      padding: 10px 25px;
      transition: 0.3s;
    }

    .btn-report:hover {
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
<%
  session = request.getSession(false);
  String fullname = null;
  if (session != null) {
    fullname = (String) session.getAttribute("fullname");
  }

  if (fullname == null) {
    response.sendRedirect("Login.jsp");
    return;
  }
%>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top shadow-sm">
  <div class="container">
    <a class="navbar-brand fw-bold" href="#">📚 LibMan Library Manager</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto align-items-center">
        <li class="nav-item text-white me-3">
          Manager <strong><%= fullname %></strong>
        </li>
        <li class="nav-item"><a class="nav-link" href="Login.jsp">Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container dashboard">
  <div class="text-center mb-5">
    <h1 class="fw-bold text-white">Select Statistic Report Type</h1>
  </div>

  <div class="d-flex flex-column align-items-center gap-3 mt-5">
    <a href="#" class="btn btn-report btn-lg">
      Statistics of Documents by Number of Borrowings
    </a>
    <a href="#" class="btn btn-report btn-lg">
      Statistics of Reader by Number of Borrowings
    </a>
    <a href="SelectTimeStampView.jsp" class="btn btn-report btn-lg">
      Statistics of Suppliers by Number of Supplied Documents
    </a>
  </div>

</div>

<footer>
  LibMan Library Management System — Manager Dashboard
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
