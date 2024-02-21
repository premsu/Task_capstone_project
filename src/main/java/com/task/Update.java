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

public class Update extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            PreparedStatement pst = con.prepareStatement("SELECT * FROM employee where id=?");
            pst.setString(1, id);
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
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Display employee details
            out.println("<h1>Employee Profile</h1>");
            while (rs.next()) {
                out.println("<form method='post'>");
                out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'/>");
                out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "'/><br/>");
                out.println("Position: <input type='text' name='position' value='" + rs.getString("position") + "'/><br/>");
                out.println("Email: <input type='text' name='email' value='" + rs.getString("email") + "'/><br/>");
                out.println("Salary: <input type='text' name='salary' value='" + rs.getDouble("salary") + "'/><br/>");
                out.println("<input type='submit' value='Update'/>");
                out.println("</form>");
            }

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String email = request.getParameter("email");
        double salary = Double.parseDouble(request.getParameter("salary"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            PreparedStatement pst = con.prepareStatement("UPDATE employee SET name=?, position=?, email=?, salary=? WHERE id=?");
            pst.setString(1, name);
            pst.setString(2, position);
            pst.setString(3, email);
            pst.setDouble(4, salary);
            pst.setString(5, id);
            pst.executeUpdate();
            response.sendRedirect("profile");
            // Close resources
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}