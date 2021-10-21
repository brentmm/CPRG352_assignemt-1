package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException { 
        
        HttpSession session = request.getSession(); //getting session

        String s_username = (String) session.getAttribute("sessionUser"); //grabing session variable

        if (request.getQueryString() != null) { //checking if user selected to logout
            if (request.getQueryString().equals("logout")) {
                session.invalidate(); //destroying session
                request.setAttribute("message", "You have sucessfully logged out."); //setting message to notify user of logout
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); //loading login page
                return;

            }
        }

        if (s_username != null) { //checking if user is logged in, if true redirects to home page
            response.sendRedirect("inventory");
            return;
        }

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
        
//        AccountService userlogin = new AccountService(); //creating instance of AccountService
//        User loginInfo = userlogin.login(username, password); //calling login method to valid login info

//        if (loginInfo == null) { //Checking if login info was correct
//            request.setAttribute("message", "Invalid login info");
//            //display form again
//            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//            //after reload stop rest of execution
//            return;
//        }
        
        HttpSession session = request.getSession(); //getting session

        session.setAttribute("sessionUser", username); //setting session variable to username

        
        response.sendRedirect("inventory"); //redirecting to home page
        return;

    }

}
