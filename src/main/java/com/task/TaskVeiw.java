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

public class TaskVeiw extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root")) {
                PreparedStatement pst = con.prepareStatement("SELECT * FROM task WHERE id=?");
                pst.setString(1, id);

                ResultSet rs = pst.executeQuery();

                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("<title>Task Details</title>");
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

                out.println("<a href=\"emp.jsp\" class=\"back-btn\">Back</a>");
                out.println("<h1>Task Details</h1>");
                out.println("<ul>");
                while (rs.next()) {
                    out.println("<li>");
                    out.println("<strong>ID:</strong> " + rs.getInt("id") + "<br>");
                    out.println("<strong>Status:</strong> " + rs.getString("status") + "<br>");
                    out.println("<strong>Deadline:</strong> " + rs.getDate("deadline") + "<br>");
                    out.println("<form action=\"complete\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\"" + rs.getInt("id") + "\">");
                    out.println("<input type=\"hidden\" name=\"deadline\" value=\"" + rs.getDate("deadline") + "\">");
                    out.println("<input type=\"submit\" value=\"Complete\">");
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
