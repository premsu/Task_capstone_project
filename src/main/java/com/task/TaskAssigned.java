package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskAssigned extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            // Prepare SQL statement to select all employees
            PreparedStatement pst = con.prepareStatement("SELECT id, name FROM employee");
            ResultSet rs = pst.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>Assign Task</title>");
            // Add CSS for background image and form styling
            out.println("<style>");
            out.println("body {");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    background-image: url('images/Designer.png');");
            out.println("    background-size: cover;");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("}");
            out.println(".container {");
            out.println("    width: 50%;");
            out.println("    margin: auto;");
            out.println("    padding: 20px;");
            out.println("    background-color: rgba(255, 255, 255, 0.9);");
            out.println("    border-radius: 10px;");
            out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
            out.println("}");
            out.println("h1 {");
            out.println("    text-align: center;");
            out.println("}");
            out.println("form {");
            out.println("    text-align: center;");
            out.println("}");
            out.println("label {");
            out.println("    display: block;");
            out.println("    margin-bottom: 10px;");
            out.println("}");
            out.println("select, input {");
            out.println("    width: calc(100% - 20px);");
            out.println("    padding: 10px;");
            out.println("    margin-bottom: 20px;");
            out.println("    border: 1px solid #ccc;");
            out.println("    border-radius: 5px;");
            out.println("}");
            out.println("input[type='submit'] {");
            out.println("    background-color: #4CAF50;");
            out.println("    color: white;");
            out.println("    cursor: pointer;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Form for assigning task
            out.println("<div class=\"container\">");
            out.println("<h1>Assign Task</h1>");
            out.println("<form method='post'>");
            out.println("<label for='employee'>Select Employee:</label>");
            out.println("<select name='employeeId'>");

            // Populate dropdown with employee details
            while (rs.next()) {
                int employeeId = rs.getInt("id");
                String employeeName = rs.getString("name");
                out.println("<option value='" + employeeId + "'>" + employeeName +"-"+ employeeId+ "</option>");
            }

            out.println("</select><br/>");
            out.println("<label for='status'>Status:</label>");
            out.println("<input type='text' name='status'/><br/>");
            out.println("<label for='deadline'>Deadline:</label>");
            out.println("<input type='date' name='deadline'/><br/>");
            out.println("<input type='submit' value='Assign'/>");
            out.println("</form>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions
           
            out.println("Error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        // Retrieve form data
        Integer employeeId = Integer.parseInt(request.getParameter("employeeId"));
        String status = request.getParameter("status");
        String deadlineStr = request.getParameter("deadline");

        try {
            // Parse deadline string into java.sql.Date
            Date deadline = Date.valueOf(deadlineStr);

            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            // Prepare SQL statement
            PreparedStatement pst = con.prepareStatement("INSERT INTO task (id, status, deadline) VALUES (?, ?, ?)");
            pst.setInt(1, employeeId);
            pst.setString(2, status);
            pst.setDate(3, deadline);

            // Execute SQL statement
            pst.executeUpdate();

            // Close resources
            pst.close();
            con.close();
            out.println("sucessful assigned");
            // JavaScript code to wait for 2 seconds and then redirect
            out.println("<script>");
            out.println("setTimeout(function() { window.location.href = 'index.html'; }, 2000);");
            out.println("</script>");

        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions
            out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle invalid date format
            out.println("Error: Invalid date format for deadline");
        }
    }
}
