import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class StudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "password");
            PreparedStatement ps = con.prepareStatement("INSERT INTO students(name,email) VALUES(?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            response.sendRedirect("index.html");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("list".equals(action)) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "password");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM students");

                PrintWriter out = response.getWriter();
                out.println("<h2>Student List</h2>");
                while(rs.next()) {
                    out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getString("email") + "<br>");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}