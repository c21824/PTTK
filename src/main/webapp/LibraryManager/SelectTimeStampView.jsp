<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>LibMan - Select Report Time Range</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #4facfe, #00f2fe);
      min-height: 100vh;
    }
    .navbar { background-color: #1e293b !important; }
    .navbar-brand, .nav-link { color: #fff !important; font-weight: 500; }
    .nav-link:hover { color: #93c5fd !important; }
    .form-card {
      background: white;
      border-radius: 20px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
      max-width: 640px;
      margin: 120px auto;
      padding: 36px;
    }
    h1 { font-weight: 600; color: #1e3a8a; margin-bottom: 22px; }
    .btn-submit {
      background-color: #2563eb; color: white; border-radius: 50px; padding: 10px 25px; transition: 0.3s;
    }
    .btn-submit:hover { background-color: #1e40af; transform: scale(1.05); }
    .btn-back { background-color: #6b7280; color: white; border-radius: 50px; padding: 10px 25px; transition: 0.3s; }
    .btn-back:hover { background-color: #4b5563; transform: scale(1.05); }
    footer { position: fixed; bottom: 10px; width: 100%; text-align: center; color: white; font-size: 14px; }
    .small-muted { color: #6b7280; font-size: 0.92rem; margin-top: 6px; display:block; text-align:left; }
  </style>
</head>
<body>

<%
  String fullname = null;
  if (session != null) {
    fullname = (String) session.getAttribute("fullname");
  }
  if (fullname == null) {
    response.sendRedirect("Login.jsp");
    return;
  }
%>

<nav class="navbar navbar-expand-lg navbar-dark fixed-top shadow-sm">
  <div class="container">
    <a class="navbar-brand fw-bold" href="#">📚 LibMan Library Manager</a>
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

<div class="form-card text-center">
  <h1>📅 Select Time Stamp for Statistics</h1>

  <form id="timeRangeForm" action="<%= request.getContextPath() %>/LibraryManager/StatisticSupplier" method="get" autocomplete="off">
    <div class="mb-3 text-start">
      <label class="form-label fw-bold">Start Date *</label>
      <input id="timeStart" type="text" name="timeStart" class="form-control" placeholder="dd/mm/yyyy" required>
      <input type="hidden" id="timeStart_iso" name="timeStart_iso">
    </div>

    <div class="mb-3 text-start">
      <label class="form-label fw-bold">End Date *</label>
      <input id="timeEnd" type="text" name="timeEnd" class="form-control" placeholder="dd/mm/yyyy" required>
      <input type="hidden" id="timeEnd_iso" name="timeEnd_iso">
    </div>

    <div class="d-flex justify-content-center gap-3 mt-4">
      <a href="SelectStatisticReportTypeView.jsp" class="btn btn-back">Back</a>
      <button id="submitBtn" type="submit" class="btn btn-submit">Continue</button>
    </div>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<script>
  function parseDMYtoDate(dmy) {
    if (!dmy) return null;
    const parts = dmy.split('/');
    if (parts.length !== 3) return null;
    const d = parseInt(parts[0], 10);
    const m = parseInt(parts[1], 10) - 1;
    const y = parseInt(parts[2], 10);
    if (isNaN(d) || isNaN(m) || isNaN(y)) return null;
    return new Date(y, m, d);
  }

  function dmyToIso(dmy) {
    const dt = parseDMYtoDate(dmy);
    if (!dt) return "";
    const y = dt.getFullYear();
    const m = String(dt.getMonth() + 1).padStart(2, '0');
    const d = String(dt.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
  }

  flatpickr("#timeStart", {
    dateFormat: "d/m/Y",
    allowInput: true,
    defaultDate: new Date(),
    maxDate: "today"
  });

  flatpickr("#timeEnd", {
    dateFormat: "d/m/Y",
    allowInput: true,
    defaultDate: new Date(),
    maxDate: "today"
  });

  const sInput = document.getElementById('timeStart');
  const eInput = document.getElementById('timeEnd');
  const sIso = document.getElementById('timeStart_iso');
  const eIso = document.getElementById('timeEnd_iso');
  const form = document.getElementById('timeRangeForm');

  function syncIsoFields() {
    sIso.value = dmyToIso(sInput.value);
    eIso.value = dmyToIso(eInput.value);
  }

  sInput.addEventListener('change', syncIsoFields);
  sInput.addEventListener('input', syncIsoFields);
  eInput.addEventListener('change', syncIsoFields);
  eInput.addEventListener('input', syncIsoFields);
  document.addEventListener('DOMContentLoaded', syncIsoFields);

  form.addEventListener('submit', function(e) {
    const s = sInput.value && sInput.value.trim();
    const en = eInput.value && eInput.value.trim();

    if (!s || !en) {
      alert('Yêu cầu chọn Start Date và End Date.');
      e.preventDefault();
      return;
    }

    const sDate = parseDMYtoDate(s);
    const eDate = parseDMYtoDate(en);

    if (!sDate || !eDate) {
      alert('Sai định dạng (dd/mm/yyyy).');
      e.preventDefault();
      return;
    }

    // làm tròn giờ để so sánh chỉ theo ngày
    const today = new Date();
    today.setHours(0,0,0,0);
    const eCompare = new Date(eDate.getFullYear(), eDate.getMonth(), eDate.getDate());

    if (sDate > eDate) {
      alert('Start Date cannot be after End Date');
      e.preventDefault();
      return;
    }

    if (eCompare > today) {
      alert('End Date cannot be after today.');
      e.preventDefault();
      return;
    }

    syncIsoFields();
  });
</script>
</body>
</html>
