
package org;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Logout", urlPatterns = {"/logout"})
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("ID", null);       
        session.setAttribute("NAME", null);
        session.setAttribute("UTYPE", null);
        session.removeAttribute("ID");
        session.removeAttribute("NAME");
        session.removeAttribute("UTYPE");        
        session.invalidate();

        HttpSession sess = request.getSession(true);
        sess.setAttribute("MSG", "Logout Successfuly.!");
        sess.setAttribute("UTYPE", "");
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }
}
