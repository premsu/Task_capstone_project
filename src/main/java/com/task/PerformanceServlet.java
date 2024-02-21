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

public class PerformanceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            // Query to get total tasks and completed tasks for each employee
            PreparedStatement pst = con.prepareStatement("SELECT id, COUNT(id) AS total_tasks, SUM(CASE WHEN status = 'completed' THEN 1 ELSE 0 END) AS completed_tasks FROM task GROUP BY id");
            ResultSet rs = pst.executeQuery();

            // Display performance     out.println("<!DOCTYPE html>");
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
            
            out.println("<a href=\"index.html\" class=\"back-btn\">Back</a> ");

            out.println("<h1>Performance</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Employee ID</th><th>Total Tasks</th><th>Completed Tasks</th><th>Performance Percentage</th></tr>");
            while (rs.next()) {
                int taskId = rs.getInt("id");
                int totalTasks = rs.getInt("total_tasks");
                int completedTasks = rs.getInt("completed_tasks");
                double performancePercentage = totalTasks > 0 ? ((double) completedTasks / totalTasks) * 100 : 0;

                out.println("<tr>");
                out.println("<td>" + taskId + "</td>");
                out.println("<td>" + totalTasks + "</td>");
                out.println("<td>" + completedTasks + "</td>");
                out.println("<td>" + performancePercentage + "%</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
