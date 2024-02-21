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
import java.sql.Date;

public class Completed extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String deadlineStr = request.getParameter("deadline");
        Date deadline = Date.valueOf(deadlineStr);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root")) {
                String sql = "UPDATE task SET status = 'completed' WHERE id = ? AND deadline = ?";
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, id);
                    pst.setDate(2, deadline);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        response.sendRedirect("emp.jsp");
                    } else {
                        out.println("Task with ID " + id + " and deadline " + deadline + " not found.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Log the error for debugging
            e.printStackTrace();
            // Provide a user-friendly error message
            out.println("An error occurred while processing your request.");
        }
    }
}
