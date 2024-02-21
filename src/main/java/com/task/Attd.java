package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Attd extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "root");
            PreparedStatement pst = con.prepareStatement(
                    "SELECT attendance_date ,employee_id,attendance_status FROM employee_attendance");

            ResultSet rs = pst.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>Employee Profiles</title>");
            // Add CSS for background image
            out.println("<style>");
            out.println("body {");
            out.println("background-image: url('images/Designer.png');"); // Change 'path_to_your_image.jpg' to the actual path of your image
            out.println("background-size: cover;");
            out.println("}");
            out.println(" .back-btn {");
            out.println("    position: absolute;");
            out.println("    top: 20px;");
            out.println("    padding: 10px 20px;");
            out.println("    border-radius: 5px;");
            out.println("}");
            out.println(".back-btn {");
            out.println("    right: 20px;");
            out.println("    background-color: #28a745;");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("}");
            out.println("table {");
            out.println(" background-color:white;");
            out.println("    border-collapse: collapse;");
            out.println("    width: 100%;");
            out.println("}");
            out.println("th, td {");
            out.println("    text-align: left;");
            out.println("    padding: 8px;");
            out.println("}");
            out.println("th {");
            
            out.println("    color: black;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Back button
            out.println("<a href=\"index.html\" class=\"back-btn\">Back</a> ");

            // Display employee details in a table
            out.println("<h1>Employee Profiles</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Date</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("employee_id") + "</td>");
                out.println("<td>" + rs.getDate("attendance_date") + "</td>");
                out.println("<td>" + rs.getString("attendance_status") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<script>");
            out.println("function goBack() {");
            out.println("  window.history.back();"); 
            out.println("}");
            out.println("</script>");

            out.println("</body>");
            out.println("</html>");

            // Close resources
            pst.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
