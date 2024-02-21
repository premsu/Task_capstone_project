package com.task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Attendence extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String attendanceStatus = request.getParameter("attendanceStatus");
        String attendanceDate = request.getParameter("attendanceDate");

        String errorMessage = null;
        String successMessage = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "root");

            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO employee_attendance (attendance_date, employee_id, attendance_status) VALUES (?, ?, ?)");
            pst.setString(1, attendanceDate);
            pst.setString(2, id);
            pst.setString(3, attendanceStatus);
            pst.executeUpdate();
            
            successMessage = "Attendance for employee ID " + id + " has been recorded as " + attendanceStatus + " on " + attendanceDate + ".";

            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            errorMessage = "Error: " + e.getMessage();
        }

        // Set attributes for error and success messages
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("successMessage", successMessage);

        // Forward the request to the JSP page
        request.getRequestDispatcher("emp.jsp").forward(request, response);
    }
}
