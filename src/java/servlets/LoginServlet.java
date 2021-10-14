package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException { 

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); //loads login page
        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String username = request.getParameter("username"); //fills login page input boxes
        String password = request.getParameter("password");

        if (username == null || username.equals("") && password == null || password.equals("")) { //checking user entered username and pass
            request.setAttribute("message", "Please enter your username and password");
            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }

        request.setAttribute("username", username);//setting values of textboxes
        request.setAttribute("password", password);
        
        response.sendRedirect("home"); //redirecting to home page
        return;

    }

}
