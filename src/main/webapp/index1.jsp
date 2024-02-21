<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image: url('images/Designer.png');
            background-size: cover;
            background-position: center;
        }

        form {
            width: 300px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        input[type="text"], input[type="email"], input[type="password"], textarea {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"], input[type="button"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover, input[type="button"]:hover {
            background-color: #0056b3;
        }

        #error-msg {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<form action="login" method="post">
    <%  
        // Get the error message from the request attribute
        String error = (String)request.getAttribute("error");
        if(error != null && !error.isEmpty()) {
    %>
    <div id="error-msg">
        <%= error %>
    </div>
    <% } %>

    <h2>Login</h2>
    <input type="text" name="id" placeholder="ID" required>
    <input type="password" name="password" placeholder="Password" required>
    <input type="submit" value="Submit">
    <input type="button" value="HR Login" onclick="location.href='index.jsp';">

</form>

</body>
</html>
