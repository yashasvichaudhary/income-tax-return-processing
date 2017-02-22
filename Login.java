
package org;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpSession;


@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    static Connection con = null;//initiating instance of the interface connection
    PreparedStatement pst = null;
    ResultSet rst = null;
    String name = null;
    String uname = null;
    String password = null;
    String result = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        uname = request.getParameter("txtuname");
        password = request.getParameter("txtpwd");
        try {
            con = DB.Connect.openConnection();
            String query = "SELECT userid,fname,lname,usertype FROM tbluser WHERE emailid = '" + uname + "' AND password = '" + password + "' AND STATUS ='True'";
            System.out.println(query);
            pst = con.prepareStatement(query);// instance of prepared statement pst is forwarding the query 
            rst = pst.executeQuery();
            if (rst.next()) {

                session.setAttribute("ID", rst.getString(1));
                session.setAttribute("UNAME", uname);
                session.setAttribute("NAME", rst.getString(2) + " " + rst.getString(3));
                session.setAttribute("UTYPE", rst.getString(4));
                if (rst.getString(4).equals("admin")) {
                    response.sendRedirect("userlist.jsp");
                } else {
                    response.sendRedirect("myaccount.jsp");
                }

            } else {
                session.setAttribute("MSG", "User name and password are wrong.!Retry Again");
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            session.setAttribute("MSG", "User name and password are wrong.!Retry Again");
            response.sendRedirect("login.jsp");
        }
    }
}
