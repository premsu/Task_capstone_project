package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            // Get parameters from the request
            String employeeId = request.getParameter("id");
            java.sql.Date startDate = java.sql.Date.valueOf(request.getParameter("start_date"));
            java.sql.Date endDate = java.sql.Date.valueOf(request.getParameter("end_date"));
            String status = "processing"; // Default status when leave is submitted

            // Insert the leave request into the database
            PreparedStatement pst = con.prepareStatement("INSERT INTO leaves (employee_id, start_date, end_date, status) VALUES (?, ?, ?, ?)");
            pst.setString(1, employeeId);
            pst.setDate(2, startDate);
            pst.setDate(3, endDate);
            pst.setString(4, status);
            pst.executeUpdate();

            // Display success message
            request.setAttribute("successMessageLeave", "Leave request submitted successfully!");
            request.getRequestDispatcher("emp.jsp").forward(request, response);

            // Close resources
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to doPost method
        doPost(request, response);
    }
}
