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

public class HrPageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "root");

            // Retrieve attendance
            PreparedStatement attendanceStatement = con.prepareStatement("SELECT * FROM attendance");
            ResultSet attendanceResult = attendanceStatement.executeQuery();

            // Retrieve leave notifications
            PreparedStatement leaveStatement = con.prepareStatement("SELECT * FROM leave_notifications");
            ResultSet leaveResult = leaveStatement.executeQuery();

            // Start HTML output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>HR Page</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; }");
            out.println(".container { display: flex; justify-content: space-between; margin: 20px; }");
            out.println(".left-column, .right-column { width: 45%; background-color: white; padding: 20px; box-sizing: border-box; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
            out.println("th, td { padding: 8px; border-bottom: 1px solid #ddd; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println(".approve-btn, .reject-btn { padding: 8px 16px; margin-right: 10px; border: none; cursor: pointer; }");
            out.println(".approve-btn { background-color: #4CAF50; color: white; }");
            out.println(".reject-btn { background-color: #f44336; color: white; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Container for layout
            out.println("<div class='container'>");

            // Left column: Attendance details
            out.println("<div class='left-column'>");
            out.println("<h2>Attendance Details</h2>");
            out.println("<table>");
            out.println("<tr><th>Employee ID</th><th>Date</th><th>Status</th></tr>");
            while (attendanceResult.next()) {
                out.println("<tr>");
                out.println("<td>" + attendanceResult.getString("employee_id") + "</td>");
                out.println("<td>" + attendanceResult.getString("date") + "</td>");
                out.println("<td>" + attendanceResult.getString("status") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div>"); // End left-column

            // Right column: Leave notifications with approve/reject buttons
            out.println("<div class='right-column'>");
            out.println("<h2>Leave Notifications</h2>");
            out.println("<table>");
            out.println("<tr><th>Employee ID</th><th>Leave Type</th><th>Start Date</th><th>End Date</th><th>Status</th><th>Action</th></tr>");
            while (leaveResult.next()) {
                out.println("<tr>");
                out.println("<td>" + leaveResult.getString("employee_id") + "</td>");
                out.println("<td>" + leaveResult.getString("leave_type") + "</td>");
                out.println("<td>" + leaveResult.getString("start_date") + "</td>");
                out.println("<td>" + leaveResult.getString("end_date") + "</td>");
                out.println("<td>" + leaveResult.getString("status") + "</td>");
                // Approve/reject buttons
                out.println("<td>");
                out.println("<form method='post' action='approveLeave'>");
                out.println("<input type='hidden' name='leaveId' value='" + leaveResult.getString("id") + "'>");
                out.println("<button class='approve-btn' type='submit'>Approve</button>");
                out.println("<button class='reject-btn' type='submit'>Reject</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div>"); // End right-column

            out.println("</div>"); // End container

            // Close resources
            attendanceStatement.close();
            leaveStatement.close();
            attendanceResult.close();
            leaveResult.close();
            con.close();

            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handling leave approval/rejection request
        String leaveId = request.getParameter("leaveId");
        String action = request.getParameter("action"); // Assuming action is passed to differentiate between approve/reject buttons

        // Logic to update leave status in the database based on leaveId and action

        // Redirect back to the HR page
        response.sendRedirect("hrPage");
    }
}
