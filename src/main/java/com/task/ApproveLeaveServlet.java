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

public class ApproveLeaveServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("leaveId"));
        String action = request.getParameter("action");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root")) {
                String sql = "UPDATE leaves SET status = ? WHERE leave_id = ?";
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, action);
                    pst.setInt(2, id); 
                    pst.executeUpdate();
                    // Print successful after successful execution
                    out.println("Successful");
                    
                    // Redirect back to the previous page
                    response.sendRedirect(request.getHeader("referer"));
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
