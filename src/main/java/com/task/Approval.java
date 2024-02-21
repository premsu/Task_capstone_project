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

public class Approval extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root")) {
                PreparedStatement pst = con.prepareStatement("SELECT * FROM leaves");

                ResultSet rs = pst.executeQuery();

                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("<title>Leave Approval</title>");
                out.println("<style>");
                out.println("body {");
                out.println("    background-image: url('images/Designer.png');");
                out.println("    background-size: cover;");
                out.println("    font-family: Arial, sans-serif;");
                out.println("}");
                out.println(".back-btn {");
                out.println("    display: inline-block;");
                out.println("    padding: 10px 20px;");
                out.println("    background-color: #007bff;");
                out.println("    color: white;");
                out.println("    text-decoration: none;");
                out.println("    border-radius: 5px;");
                out.println("    margin-bottom: 20px;");
                out.println("}");
                out.println("h1 {");
                out.println("    clear: both;");
                out.println("}");
                out.println("ul {");
                out.println("    list-style-type: none;");
                out.println("    padding: 0;");
                out.println("}");
                out.println("li {");
                out.println("    background-color: rgba(255, 255, 255, 0.8);");
                out.println("    padding: 20px;");
                out.println("    margin-bottom: 20px;");
                out.println("    border-radius: 5px;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");

                out.println("<a href=\"index.html\" class=\"back-btn\">Back</a>");
                out.println("<h1>Leave Approval</h1>");
                out.println("<ul>");
                while (rs.next()) {
                    out.println("<li>");
                    out.println("<strong>Leave ID:</strong> " + rs.getInt("leave_id") + "<br>");
                    out.println("<strong>Employee ID:</strong> " + rs.getInt("employee_id") + "<br>");
                    out.println("<strong>Start Date:</strong> " + rs.getDate("start_date") + "<br>");
                    out.println("<strong>End Date:</strong> " + rs.getDate("end_date") + "<br>");
                    out.println("<strong>Status:</strong> " + rs.getString("status") + "<br>");
                    out.println("<form action=\"approval\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"leaveId\" value=\"" + rs.getInt("leave_id") + "\">");
                    out.println("<input type=\"hidden\" name=\"action\" value=\"approved\">");
                    out.println("<input type=\"submit\" value=\"Approve\">");
                    out.println("</form>");
                    out.println("<form action=\"approval\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"leaveId\" value=\"" + rs.getInt("leave_id") + "\">");
                    out.println("<input type=\"hidden\" name=\"action\" value=\"rejected\">");
                    out.println("<input type=\"submit\" value=\"Reject\">");
                    out.println("</form>");
                    out.println("</li>");
                }
                out.println("</ul>");

                out.println("</body>");
                out.println("</html>");

                // Close resources
                rs.close();
                pst.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
