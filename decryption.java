
package org;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Decryption", urlPatterns = {"/decryption"})
public class Decryption extends HttpServlet {

    static Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    String file_name = null;
    String fullImagepath = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/xml");
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();

        String uid = (String) session.getAttribute("ID");



        ServletConfig config = getServletConfig();
        String context = config.getServletContext().getRealPath("/");
        String filePath = context + "uploadfiles";

        //connection from database and fetching the file id and pwd.
        try {
            con =DB.Connect.openConnection();
        } catch (Exception e) {
        }

        String fileid = request.getParameter("fileid");
        String pwd = request.getParameter("pwd");
        String filename = "";

        try {
		//fetching the filepath from the database.
            con = DB.Connect.openConnection();
            String query = "SELECT filepath  FROM tblfiles  where id ='" + fileid + "'";
            System.out.println(query);
            Statement st = con.prepareStatement(query);
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                filename = result.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		//The full image path is fetched here.
        fullImagepath = filePath + File.separator + filename;
 String temp="temp"+filename.substring(filename.lastIndexOf("."));
        System.out.println("temp file="+temp);
        String newfilepath = filePath + File.separator + temp;
        //System.out.println("fullImagepath aaaaaaaaaa: " + fullImagepath);
		//The main file is getting encrypted here.
        boolean flag;
        if (fullImagepath.equals("") || fullImagepath.equals("nullnull")) {
            flag = new MainEncryption().DBST(3, fullImagepath, pwd, newfilepath);
        } else {
            flag = new MainEncryption().DBST(2, fullImagepath, pwd, newfilepath);

        }
		//a flag to show the status.
        if (flag) {
            out.write("<status><sname>"+temp+"</sname></status>");
        } else {
            out.write("<status><sname>res</sname></status>");
        }

    }
}
