<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Workforce Harmony - Main Menu</title>
  <style>
    /* Resetting default margin and padding */
    body, ul {
      margin: 0;
      padding: 0;
    }

    /* Set background image */
    body {
      background-image: url('images/Designer.png'); /* Replace 'background.jpg' with the path to your image */
      background-size: cover; /* Cover the entire viewport */
      background-repeat: no-repeat; /* Do not repeat the image */
    }

    /* Styles for menu bar */
    .menu-bar {
      background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
      overflow: hidden;
      padding: 10px 20px;
      position: fixed;
      top: 0;
      width: 100%;
      z-index: 2; /* Ensure menu bar is on top */
    }
    .menu-bar a {
      float: left;
      display: block;
      color: white;
      text-align: center;
      padding: 14px 16px;
      text-decoration: none;
      transition: background-color 0.3s;
    }
    .menu-bar a.logout-btn {
      float: right; /* Align logout button to the right */
    }
    .menu-bar a:hover {
      background-color: rgba(255, 255, 255, 0.3); /* Semi-transparent background on hover */
    }

    /* Styles for navigation bar */
    .nav-bar {
      background-color: rgba(255, 255, 255, 0.9); /* Semi-transparent white background */
      padding: 20px;
      position: fixed;
      top: 70px;
      left: -100%; /* Off-screen initially */
      width: 300px; /* Width of navigation bar */
      height: 100%;
      overflow: hidden;
      transition: left 0.3s ease-out;
      z-index: 1; /* Ensure navigation bar is below menu bar */
    }
    .nav-bar a {
      display: block;
      color: #333;
      text-align: left;
      padding: 14px 16px;
      text-decoration: none;
      transition: background-color 0.3s;
    }
    .nav-bar a:hover {
      background-color: #ddd;
    }

    /* Close button */
    .close-btn {
      float: right;
      cursor: pointer;
      padding: 14px 16px;
    }

    /* ID display */
    .user-id {
      color: white;
      text-align: center;
      padding: 10px 0;
    }

    /* Attendance Form */
    #attendanceForm, #leaveForm {
      display: none;
      position: fixed;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      background: white;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
      z-index: 3; /* Ensure forms are on top of other elements */
    }
    #attendanceForm h1, #leaveForm h1 {
      text-align: center;
    }
    #attendanceForm form, #leaveForm form {
      text-align: center;
    }

    /* Success message */
    .successMessage {
      color: green;
      text-align: center;
      margin-bottom: 10px;
      position: relative;
      top: -20px;
    }
  </style>
</head>
<body>

<!-- Menu Bar -->
<div class="menu-bar">
  <a href="#" class="menu-btn">☰ Menu</a>
  <a href="#" class="logout-btn" onclick="confirmLogout()">Logout</a>
</div>

<!-- Display User ID -->
<div class="user-id">
  <% 
    // Get user ID from session
    String userID = (String) session.getAttribute("id");
    if (userID != null) {
        out.println("Welcome, ID: " + userID);
    }
  %>
</div>

<!-- Navigation Bar -->
<div class="nav-bar" id="navBar">
  <span class="close-btn" onclick="closeNav()">×</span>
  <a href="about?id=<%= userID %>">Profile</a>
  <a href="Taskveiw?id=<%= userID %>">task to do</a>
  <a href="#" onclick="showAttendanceForm()">Record Attendance</a>
  <a href="#" onclick="showLeaveForm()">Leave Request</a>
  <a href="leavetrack?id=<%= userID %>">Leave tracker</a>
  <a href="#">Skill & Training</a>
  <a href="#" class="logout-btn" onclick="confirmLogout()">Logout</a>
</div>

<!-- Attendance Form Section -->
<div id="attendanceForm">
  <%-- Check if success message exists in request attribute --%>
  <div class="successMessage" id="attendanceSuccessMessage"></div>
  
  <h1>Record Attendance</h1>
  <form action="attendance" method="post">
    <input type="hidden" name="id" value="<%= userID %>">
    <input type="hidden" name="attendanceDate" value="<%= java.time.LocalDate.now() %>">
    <label for="attendanceStatus">Attendance Status:</label>
    <select id="attendanceStatus" name="attendanceStatus">
      <option value="Present">Present</option>
      <option value="Absent">Absent</option>
    </select>
    <br><br>
    <input type="submit" value="Confirm">
  </form>
</div>

<!-- Leave Request Form Section -->
<div id="leaveForm">
  <div class="successMessage" id="leaveSuccessMessage"></div>

  <h1>Request Leave</h1>
  <form action="leave" method="post">
    <input type="hidden" name="id" value="<%= userID %>">
    <label for="start_date">Start Date:</label>
    <input type="date" id="start_date" name="start_date" min="<%= java.time.LocalDate.now() %>" required>
    <br><br>
    <label for="end_date">End Date:</label>
    <input type="date" id="end_date" name="end_date" min="<%= java.time.LocalDate.now() %>" required>
    <br><br>
    <input type="submit" value="Submit">
  </form>
</div>

<script>
  // Function to close navigation bar
  function closeNav() {
    document.getElementById("navBar").style.left = "-100%"; /* Move off-screen */
  }

  // Function to confirm logout
  function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
      // Redirect to the login page
      window.location.href = "index1.jsp"; // Change "index1.jsp" to the actual login page URL
    }
  }

  // Toggle navigation bar
  document.querySelector('.menu-btn').addEventListener('click', function() {
    var navBar = document.getElementById("navBar");
    if (navBar.style.left === "-100%" || navBar.style.left === "") { /* Check if off-screen */
      navBar.style.left = "0"; /* Move in */
    } else {
      navBar.style.left = "-100%"; /* Move off-screen */
    }
  });

  // Function to show attendance form
  function showAttendanceForm() {
    document.getElementById("attendanceForm").style.display = "block";
  }

  // Function to show leave request form
  function showLeaveForm() {
    document.getElementById("leaveForm").style.display = "block";
  }

  // Function to display success message for attendance
  function showAttendanceSuccessMessage(message) {
    var successMessage = document.getElementById("attendanceSuccessMessage");
    successMessage.innerHTML = message;
    setTimeout(function() {
      successMessage.innerHTML = "";
    }, 3000); // Clear message after 3 seconds
  }

  // Function to display success message for leave request
  function showLeaveSuccessMessage(message) {
    var successMessage = document.getElementById("leaveSuccessMessage");
    successMessage.innerHTML = message;
    setTimeout(function() {
      successMessage.innerHTML = "";
    }, 3000); // Clear message after 3 seconds
  }
</script>

</body>
</html>
