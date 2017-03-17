//registration page for the user 
package org;
import DB.SMSSender;
import DB.SimpleEmail;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(name = "Register", urlPatterns = {"/register"})
public class Register extends HttpServlet {

    static Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    String fname = null;
    String lname = null;
    String dob = null;
    String username = null;
    String email = null;
    String contno = null;
    String location = null;
    String city = null;
    String pwd = null;
    int i = 0;

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);

        try {
            con = DB.Connect.openConnection();
            String query = "UPDATE tbluser SET status='False' WHERE userid= '" + id + "' ";
            pst = con.prepareStatement(query);
            i = pst.executeUpdate();

        } catch (Exception e) {
        }

        if (i > 0) {
            session.setAttribute("MSG", "User has been successfuly deleted !!");
            response.sendRedirect("userlist.jsp");
        } else {
            session.setAttribute("MSG", "User has not been deleted !!");
            response.sendRedirect("userlist.jsp");
        }
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);// It will return the current session.
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        ServletConfig config = getServletConfig();
        String context = config.getServletContext().getRealPath("/");

        //connection from database
        try {
            con = DB.Connect.openConnection();
        } catch (Exception e) {
        }


        fname = request.getParameter("txtfname");
        lname = request.getParameter("txtlname");
        username = request.getParameter("txtusername");
        email = request.getParameter("txtemail");
        contno = request.getParameter("txtcontno");
        location = request.getParameter("txtlocation");
        city = request.getParameter("txtcity");
       Random ran = new Random();
String code= Integer.toString(100000 + ran.nextInt(999999));

System.out.println("code="+code);
        try {
            i=DB.Connect.saveUsers(fname, lname, username, email, location, "True", "user", code, contno);
DB.SimpleEmail email1=new SimpleEmail();
   email1.setMailServerProperties();
            email1.createEmailMessage("Registration Successfull","Your password is: "+code, email);
            email1.sendEmail();               
            String value= SMSSender.SMSSender("onlinefood", "You have successfully registered in Income tax. Your Login id is: "+email+" and password is:"+code , "FOODAP",contno, "167019f3-3fcb-423c-80ef-f7b51237c644");
   System.out.println("message sent: "+value);
        } catch (Exception e) {
            e.printStackTrace();;
        }

        //success or failure message
        if (i > 0) {

            session.setAttribute("MSG", "Your register form has been successfully submited.Please check your mail id");
            response.sendRedirect("register.jsp");
        } else {
            session.setAttribute("MSG", "Your register form has not been submited.");
            response.sendRedirect("register.jsp");
        } 


    }
}
