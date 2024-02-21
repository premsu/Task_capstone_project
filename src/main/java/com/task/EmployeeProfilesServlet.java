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

public class EmployeeProfilesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "root");
            PreparedStatement pst = con.prepareStatement(
                    "SELECT id,name,email FROM employee");

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
            out.println(".add-btn, .back-btn {");
            out.println("    position: absolute;");
            out.println("    top: 20px;");
            out.println("    padding: 10px 20px;");
            out.println("    border-radius: 5px;");
            out.println("}");
            out.println(".add-btn {");
            out.println("    right: 100px;");
            out.println("    background-color: #007bff;");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("}");
            out.println(".back-btn {");
            out.println("    right: 20px;");
            out.println("    background-color: #28a745;");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<a href=\"add\" class=\"add-btn\">Add</a>");
            // Back button
            out.println("<a href=\"index.html\" class=\"back-btn\">Back</a> ");
            // Add button
            

            // Display employee details
            out.println("<h1>Employee Profiles</h1>");
            out.println("<ul>");
            while (rs.next()) {
                out.println("<li>");
                out.print("<strong>Id:</strong>"+rs.getInt("id")+"<br>");
                out.println("<strong>Name:</strong> " + rs.getString("name") + "<br>");
                out.println("<strong>Email:</strong> " + rs.getString("email") + "<br>");
                out.println("<a href=\"view?id=" + rs.getInt("id") + "\">View</a> | ");
                out.println("<a href=\"Edit?id="+rs.getInt("id")+"\">Edit</a> |" );
                out.println("<a href=\"delete?id=" + rs.getInt("id") + "\" onclick=\"return confirm('Are you sure you want to delete this employee?');\">Delete</a>");
                out.println("</li>");
                out.println("</li>");
                out.println("<br>");
            }
            out.println("</ul>");

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
