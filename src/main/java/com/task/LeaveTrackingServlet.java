package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeaveTrackingServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT employee_id, start_date, end_date, status FROM leaves WHERE employee_id = ?";
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, id);
                    try (ResultSet rs = pst.executeQuery()) {
                        out.println(getHTMLHeader());
                        out.println("<body>");
                        out.println("<a href=\"emp.jsp\" class=\"back-btn\">Back</a>");
                        out.println("<h1>Employee Profiles</h1>");

                        // Separate lists for approved and processing leaves
                        List<String[]> approvedLeaves = new ArrayList<>();
                        List<String[]> processingLeaves = new ArrayList<>();

                        // Iterate through the result set and store data in appropriate lists
                        while (rs.next()) {
                            String[] rowData = new String[4];
                            rowData[0] = Integer.toString(rs.getInt("employee_id"));
                            rowData[1] = rs.getDate("start_date").toString();
                            rowData[2] = rs.getString("end_date");
                            rowData[3] = rs.getString("status");

                            if (rowData[3].equalsIgnoreCase("approved")) {
                                approvedLeaves.add(rowData);
                            } else {
                                processingLeaves.add(rowData);
                            }
                        }

                        // Print approved leaves
                        out.println("<h2>Approved Leaves</h2>");
                        out.println("<table>");
                        out.println("<tr><th>Id</th><th>Start Date</th><th>End Date</th><th>Status</th></tr>");
                        for (String[] row : approvedLeaves) {
                            out.println("<tr>");
                            out.println("<td>" + row[0] + "</td>");
                            out.println("<td>" + row[1] + "</td>");
                            out.println("<td>" + row[2] + "</td>");
                            out.println("<td><span style=\"color: green;\">&#10004;</span> " + row[3] + "</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");

                        // Print leaves under processing
                        out.println("<h2>Leaves Under Processing</h2>");
                        out.println("<table>");
                        out.println("<tr><th>Id</th><th>Start Date</th><th>End Date</th><th>Status</th></tr>");
                        for (String[] row : processingLeaves) {
                            out.println("<tr>");
                            out.println("<td>" + row[0] + "</td>");
                            out.println("<td>" + row[1] + "</td>");
                            out.println("<td>" + row[2] + "</td>");
                            out.println("<td>" + row[3] + "</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");

                        out.println(getHTMLFooter());
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    private String getHTMLHeader() {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Employee Profiles</title>" +
                "<style>" +
                "body {" +
                "    background-image: url('images/Designer.png');" +
                "    background-size: cover;" +
                "}" +
                ".back-btn {" +
                "    position: absolute;" +
                "    top: 20px;" +
                "    right: 20px;" +
                "    padding: 10px 20px;" +
                "    border-radius: 5px;" +
                "    background-color: #28a745;" +
                "    color: white;" +
                "    text-decoration: none;" +
                "}" +
                "table {" +
                "    background-color: white;" +
                "    border-collapse: collapse;" +
                "    width: 100%;" +
                "}" +
                "th, td {" +
                "    text-align: left;" +
                "    padding: 8px;" +
                "}" +
                "th {" +
                "    color: black;" +
                "}" +
                "</style>" +
                "</head>";
    }

    private String getHTMLFooter() {
        return "</body></html>";
    }
}
