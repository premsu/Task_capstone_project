<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Centered Form</title>
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
    position: relative; /* Added */
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

  input[type="submit"] {
    width: 100%;
    padding: 10px;
    margin-top: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  input[type="submit"]:hover {
    background-color: #0056b3;
  }

  #error-msg {
    color: red;
    margin-bottom: 10px;
  }

  #back-button { /* Modified */
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: #dc3545;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
</style>
</head>
<body>

<form action="fetch" method="post">
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
  <input type="text" name="username" placeholder="Username" required>
  <input type="password" name="password" placeholder="Password" required>
  <input type="submit" value="submit">
</form>

<button id="back-button" onclick="goBack()">Back</button> 

<script>
  function goBack() {
	  window.location.href = "index1.jsp";
  }
</script>

</body>
</html>
