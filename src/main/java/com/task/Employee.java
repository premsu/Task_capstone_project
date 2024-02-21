package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Employee extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "root");
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM employee WHERE id = ? AND password = ?");
            pst.setString(1, id);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // If user exists in the database, set ID in session and redirect to index page
                HttpSession session = request.getSession();
                session.setAttribute("id", id);
                response.sendRedirect("emp.jsp");
            } else {
                // If user doesn't exist, show login error
                request.setAttribute("error", "Login failed. Please check your credentials.");
                RequestDispatcher rd = request.getRequestDispatcher("index1.jsp");
                rd.forward(request, response);
            }

            // Close resources
            pst.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            pw.println("Error: " + e.getMessage());
        }
    }
}
