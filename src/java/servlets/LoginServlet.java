package servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

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

        request.setAttribute("username", username);//setting values of textboxes
        request.setAttribute("password", password);

        if (username == null || username.equals("") || password == null || password.equals("")) { //checking user entered username and pass
            request.setAttribute("message", "Please enter your username and password");
            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }

        ArrayList<User> userData = new ArrayList<>();

        //Read in users
        String path = getServletContext().getRealPath("/WEB-INF/users.txt");

        File file1 = new File(path);

        Scanner userReader = new Scanner(file1);

        while (userReader.hasNext()) {
            String temp = userReader.nextLine();
            String[] sections = temp.split(",");

            User user = new User(sections[0], sections[1]);

            userData.add(user);
        }

        userReader.close();

        //validate login
        for (int n = 0; n < userData.size(); n++) {
            if (userData.get(n).getUsername().equals(username) && username.equals("admin") && userData.get(n).getPassword().equals(password)) {
                HttpSession session = request.getSession(); //getting session

                session.setAttribute("sessionUser", username); //setting session variable to username
                session.setAttribute("sessionPass", password);

                response.sendRedirect("admin"); //redirecting to home page
                return;

            } else if (userData.get(n).getUsername().equals(username) && userData.get(n).getPassword().equals(password)) {

                System.out.println("Username:");
                System.out.println(userData.get(n).getUsername());

                HttpSession session = request.getSession(); //getting session

                session.setAttribute("sessionUser", username); //setting session variable to username
                session.setAttribute("sessionPass", password);

                response.sendRedirect("inventory"); //redirecting to home page
                return;

            }

        }

        request.setAttribute(
        "message", "Invalid login info");
        //display form again
        getServletContext()
        .getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        //after reload stop rest of execution

        return;

    }

}

//        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
//
//        for (String line = br.readLine(); line != null; line = br.readLine()) {
//            String temp = br.readLine();
//            String[] sections = temp.split(",");
//
//            User user = new User(sections[0], sections[1]);
//
//            userData.add(user);
//
//        }
//
//        br.close();

