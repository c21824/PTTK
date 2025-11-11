<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.ImportingInvoice" %>
<%@ page import="Entity.Supplier" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="utils.ConvertDate" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>LibMan - Importing Document Batch</title>

    <!-- Bootstrap -->
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

        .card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            max-width: 900px;
            margin: 130px auto;
            padding: 40px;
        }

        h1 {
            font-weight: 600;
            color: #1e3a8a;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px 12px;
            text-align: center;
        }

        th {
            background-color: #f1f5f9;
            color: #1e3a8a;
            font-weight: 600;
        }

        tr:hover {
            background-color: #f9fafb;
        }

        .btn-back {
            background-color: #6b7280;
            color: white;
            border-radius: 50px;
            padding: 8px 25px;
            margin-top: 25px;
            transition: 0.3s;
        }

        .btn-back:hover {
            background-color: #4b5563;
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
    DecimalFormat dfPrice = new DecimalFormat("#,##0.000");

    String timeStart = (String) session.getAttribute("timeStart");
    String timeEnd = (String) session.getAttribute("timeEnd");
    int totalQuantity = (int) request.getAttribute("totalQuantity");
    double totalPrice = (double) request.getAttribute("totalPrice");
    List<ImportingInvoice> importingInvoices = (List<ImportingInvoice>) session.getAttribute("importingInvoices");
    String supplierName = (String) session.getAttribute("supplierName");
%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="#">📚 LibMan Library Manager</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                <li class="nav-item text-white me-3">
                    Manager <strong><%= fullname %>
                </strong>
                </li>
                <li class="nav-item"><a class="nav-link" href="Login.jsp">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Main Card -->
<div class="card text-center">
    <h1>📦 Importing Document Batch</h1>

    <div class="row justify-content-center mb-3">
        <div class="col-md-5 text-start">
            <p><strong>Time Start:</strong> <%= timeStart %>
            </p>
            <p><strong>Time End:</strong> <%= timeEnd %>
            </p>
            <p><strong>Supplier:</strong> <%= supplierName %>
            </p>
            <p><strong>Total Quantity:</strong> <%= totalQuantity %> Documents</p>
            <p><strong>Total Price:</strong> <%= dfPrice.format(totalPrice) %> VND</p>
        </div>
    </div>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>QUANTITY</th>
            <th>PRICE</th>
            <th>DATE OF PAYMENT</th>
            <th>ACTION</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (ImportingInvoice i : importingInvoices) {
        %>
        <tr>
            <td class="text-end"><%= i.getId() %>
            </td>
            <td class="text-end"><%= i.getTotalQuantity() %>
            </td>
            <td class="text-end"><%= dfPrice.format(i.getTotalPrice()) %> VND</td>
            <td><%= ConvertDate.toDmyFromObject(i.getDateOfPayment()) %>
            </td>
            <td>
                <form action="<%= request.getContextPath() %>/LibraryManager/ImportingDocument" method="get" style="display:inline">
                    <input type="hidden" name="importingInvoiceId" value="<%= i.getId() %>">
                    <input type="hidden" name="totalQuantity" value="<%= i.getTotalQuantity() %>">
                    <input type="hidden" name="totalPrice" value="<%= i.getTotalPrice() %>">
                    <input type="hidden" name="dateOfPayment" value="<%= i.getDateOfPayment() %>">
                    <button type="submit" class="btn btn-link p-0">Select</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="d-flex justify-content-between align-items-center mt-3">
        <div>
            <form id="pageSizeFormInv" method="get" action="<%= request.getContextPath() %>/ImportingInvoice">
                <input type="hidden" name="supplierId" value="<%= (request.getParameter("supplierId")!=null?request.getParameter("supplierId"):(String)session.getAttribute("supplierId")) %>">
                <input type="hidden" name="supplierName" value="<%= supplierName %>">
                <input type="hidden" name="timeStart" value="<%= timeStart %>">
                <input type="hidden" name="timeEnd" value="<%= timeEnd %>">
                <label for="pageSizeSelectInv">Rows per page:</label>
                <select id="pageSizeSelectInv" name="pageSize" onchange="document.getElementById('pageSizeFormInv').submit()">
                    <option value="5" <%= (request.getAttribute("pageSize")!=null && (Integer)request.getAttribute("pageSize")==5)?"selected":"" %>>5</option>
                    <option value="10" <%= (request.getAttribute("pageSize")==null || (Integer)request.getAttribute("pageSize")==10)?"selected":"" %>>10</option>
                    <option value="20" <%= (request.getAttribute("pageSize")!=null && (Integer)request.getAttribute("pageSize")==20)?"selected":"" %>>20</option>
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

                        String base = request.getContextPath() + "/ImportingInvoice?supplierId=" + (request.getParameter("supplierId")!=null?request.getParameter("supplierId"):(session.getAttribute("supplierId")!=null?session.getAttribute("supplierId").toString():"")) + "&supplierName=" + java.net.URLEncoder.encode(supplierName, "UTF-8") + "&timeStart=" + java.net.URLEncoder.encode(timeStart, "UTF-8") + "&timeEnd=" + java.net.URLEncoder.encode(timeEnd, "UTF-8") + "&pageSize=" + pageSize;

                        if (currentPage > 1) {
                    %>
                    <li class="page-item"><a class="page-link" href="<%= base + "&page=" + (currentPage-1) %>">Previous</a></li>
                    <% } else { %>
                    <li class="page-item disabled"><span class="page-link">Previous</span></li>
                    <% }

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
