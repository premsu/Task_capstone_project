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

public class About extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "root");
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM employee where id="+id);

            ResultSet rs = pst.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>Employee Profiles</title>");
            out.println("<style>");
            out.println("body {");
            out.println("    background-image: url('images/Designer.png');");
            out.println("    background-size: cover;");
            out.println("}");

            out.println(".back-btn {");
            out.println("    position: absolute;");
            out.println("    top: 20px;");
            out.println("    right: 20px;");
            out.println("    padding: 10px 20px;");
            out.println("    background-color: #007bff;"); 
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 5px;");
            out.println("}");

            out.println("h1 {");
            out.println("    clear: both;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<a href=\"emp.jsp\" class=\"back-btn\">Back</a> ");
            out.println("<h1>Employee Profiles</h1>");
            out.println("<ul>");
            while (rs.next()) {
                out.println("<li>");
                out.print("<strong>Id:</strong>"+rs.getInt("id")+"<br>");
                out.println("<strong>Name:</strong> " + rs.getString("name") + "<br>");
                out.println("<strong>Position:</strong> " + rs.getString("position") + "<br>");
                out.println("<strong>Email:</strong> " + rs.getString("email") + "<br>");
                out.print("<strong>Salary:</strong>"+rs.getDouble("salary")+"<br>");
                out.print("<strong>password:</strong>"+rs.getString("password")+"<br>");

                out.println("<a href=\"Edit?id="+rs.getInt("id")+"\">Edit</a> |");
                out.println("</li>");
                out.println("</li>");
                out.println("<br>");
            }
            out.println("</ul>");

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
