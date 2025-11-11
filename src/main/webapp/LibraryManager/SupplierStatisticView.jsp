<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.SupplierStatistic" %>
<%@ page import="java.text.DecimalFormat" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>LibMan - Supplier Statistic Result</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #4facfe, #00f2fe); min-height: 100vh; }
    .card { background: white; border-radius: 20px; box-shadow: 0 8px 20px rgba(0,0,0,0.1);
      max-width: 900px; margin: 130px auto; padding: 40px; }
    th, td { border: 1px solid #ccc; padding: 10px 12px; text-align: center; }
    th { background-color: #f1f5f9; color: #1e3a8a; font-weight: 600; }
    .btn-back { background-color: #6b7280; color: white; border-radius: 50px; padding: 8px 25px; margin-top: 25px; }
    footer { position: fixed; bottom: 10px; width: 100%; text-align: center; color: white; font-size: 14px; }
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

  List<SupplierStatistic> supplierStatistics = (List<SupplierStatistic>) request.getAttribute("supplierStatistics");
  if (supplierStatistics == null) {
    supplierStatistics = new java.util.ArrayList<>();
  }

  String timeStart = (String) session.getAttribute("timeStart");
  String timeEnd   = (String) session.getAttribute("timeEnd");
  int totalQuantity = (int) request.getAttribute("totalQuantity");
  double totalPrice = (double) request.getAttribute("totalPrice");
  DecimalFormat dfPrice = new DecimalFormat("#,##0.000");

%>

<nav class="navbar navbar-expand-lg navbar-dark fixed-top shadow-sm" style="background-color:#1e293b;">
  <div class="container">
    <a class="navbar-brand fw-bold" href="#">📚 LibMan Library Manager</a>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto align-items-center">
        <li class="nav-item text-white me-3">Manager <strong><%= fullname %></strong></li>
        <li class="nav-item"><a class="nav-link" href="Login.jsp">Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="card text-center">
  <h1>📊 Statistics of Suppliers by Number of Supplied Documents</h1>

  <div class="row justify-content-center mb-4">
    <div class="col-md-4 text-start">
      <p><strong>Time Start:</strong> <%= timeStart%> </p>
      <p><strong>Time End:</strong> <%= timeEnd %> </p>
      <p><strong>Total Quantity:</strong> <%= totalQuantity %> Documents</p>
      <p><strong>Total Price:</strong> <%= dfPrice.format(totalPrice) %> VND</p>
    </div>
  </div>

  <table class="table table-bordered">
    <thead>
    <tr>
      <th>ID</th>
      <th>SUPPLIER</th>
      <th>Quantity</th>
      <th>Price</th>
      <th>ACTION</th>
    </tr>
    </thead>
    <tbody>
    <%
      for (SupplierStatistic s : supplierStatistics) {
    %>
    <tr>
      <td class="text-end"><%= s.getId() %></td>
      <td class="text-start"><%= s.getSupplierName() %></td>
      <td class="text-end"><%= s.getTotalQuantity() %></td>
      <td class="text-end"><%= dfPrice.format(s.getTotalPrice()) %> VND</td>
      <td>
        <form action="<%= request.getContextPath() %>/LibraryManager/ImportingInvoice" method="get" style="display:inline">
          <input type="hidden" name="supplierId" value="<%= s.getId() %>">
          <input type="hidden" name="supplierName" value="<%= s.getSupplierName() %>">
          <input type="hidden" name="timeStart" value="<%= timeStart %>">
          <input type="hidden" name="timeEnd" value="<%= timeEnd %>">
          <button type="submit" class="btn btn-link p-0">Select</button>
        </form>
      </td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <!-- Pagination controls -->
  <div class="d-flex justify-content-between align-items-center mt-3">
    <div>
      <form id="pageSizeForm" method="get" action="<%= request.getContextPath() %>/LibraryManager/StatisticSupplier">
        <input type="hidden" name="timeStart" value="<%= timeStart %>">
        <input type="hidden" name="timeEnd" value="<%= timeEnd %>">
        <label for="pageSizeSelect">Rows per page:</label>
        <select id="pageSizeSelect" name="pageSize" onchange="document.getElementById('pageSizeForm').submit()">
          <option value="5" <%= ((Integer) request.getAttribute("pageSize")!=null && (Integer) request.getAttribute("pageSize")==5)?"selected":"" %>>5</option>
          <option value="10" <%= ((Integer) request.getAttribute("pageSize")==null || (Integer) request.getAttribute("pageSize")==10)?"selected":"" %>>10</option>
          <option value="20" <%= (request.getAttribute("pageSize")!=null && (Integer) request.getAttribute("pageSize")==20)?"selected":"" %>>20</option>
        </select>
      </form>
    </div>

    <div>
      <nav aria-label="Page navigation">
        <ul class="pagination mb-0">
          <%
            int currentPage = request.getAttribute("currentPage") != null ? (Integer) request.getAttribute("currentPage") : 1;
            int totalPages = request.getAttribute("totalPages") != null ? (Integer) request.getAttribute("totalPages") : 1;
            int pageSize = request.getAttribute("pageSize") != null ? (Integer) request.getAttribute("pageSize") : 10;

            String base = request.getContextPath() + "/LibraryManager/StatisticSupplier?timeStart=" + java.net.URLEncoder.encode(timeStart, "UTF-8") + "&timeEnd=" + java.net.URLEncoder.encode(timeEnd, "UTF-8") + "&pageSize=" + pageSize;

            // Prev
            if (currentPage > 1) {
          %>
          <li class="page-item"><a class="page-link" href="<%= base + "&page=" + (currentPage-1) %>">Previous</a></li>
          <% } else { %>
          <li class="page-item disabled"><span class="page-link">Previous</span></li>
          <% }

            // page numbers - show up to 7 pages (current +/-3)
            int start = Math.max(1, currentPage - 3);
            int end = Math.min(totalPages, currentPage + 3);
            if (start > 1) { %>
              <li class="page-item"><a class="page-link" href="<%= base + "&page=1" %>">1</a></li>
              <% if (start > 2) { %>
                <li class="page-item disabled"><span class="page-link">...</span></li>
              <% }
            }

            for (int p = start; p <= end; p++) {
              if (p == currentPage) { %>
                <li class="page-item active" aria-current="page"><span class="page-link"><%= p %></span></li>
              <% } else { %>
                <li class="page-item"><a class="page-link" href="<%= base + "&page=" + p %>"><%= p %></a></li>
              <% }
            }

            if (end < totalPages) {
              if (end < totalPages - 1) { %>
                <li class="page-item disabled"><span class="page-link">...</span></li>
              <% }
              %>
              <li class="page-item"><a class="page-link" href="<%= base + "&page=" + totalPages %>"><%= totalPages %></a></li>
            <% }

            // Next
            if (currentPage < totalPages) {
          %>
          <li class="page-item"><a class="page-link" href="<%= base + "&page=" + (currentPage+1) %>">Next</a></li>
          <% } else { %>
          <li class="page-item disabled"><span class="page-link">Next</span></li>
          <% }
          %>
        </ul>
      </nav>
    </div>
  </div>

  <a href="javascript:history.back()" class="btn btn-back">Back</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
