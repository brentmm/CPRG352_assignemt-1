package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession session = request.getSession(); //getting session

        String s_username = (String) session.getAttribute("sessionUser"); //grabing session variable
        
        if (!s_username.equals("admin")) { //checking if user is logged in, if true redirects to home page
            response.sendRedirect("login");
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); //loads login page
        return;        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
