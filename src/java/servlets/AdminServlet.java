package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.HomeItem;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        HttpSession session = request.getSession(); //getting session

        String sess_username = (String) session.getAttribute("sessionUser"); //grabing session variable

        if (!sess_username.equals("admin")) { //checking if user is logged in, if true redirects to home page
            response.sendRedirect("login");
            return;
        }

         ArrayList<HomeItem> adminData = new ArrayList<>(); //array to store objects

        //read in total cost
        String path = getServletContext().getRealPath("/WEB-INF/homeitems.txt"); //file path

        BufferedReader br = new BufferedReader(new FileReader(new File(path))); //creating reader

        String temp;
        
       
        while ((temp = br.readLine()) != null) { //loops through reading in lines from file
//            System.out.println(temp);
            String[] adminSections = temp.split(","); //splits lines by commas

            HomeItem adminCost = new HomeItem(adminSections[0], adminSections[1],adminSections[2],adminSections[3]); //inserts values into an object

            adminData.add(adminCost); //adds object to an array
        }

        br.close();
        
        int adminSum = 0;
        
         for (int n = 0; n < adminData.size(); n++) { //loops through array of objects
             String s_itemVal = adminData.get(n).getPrice(); //gets price value of object
//             System.out.println(s_itemVal);
             
             int int_itemVal = Integer.parseInt(s_itemVal); //swaps string value of price into an integer
             
             
             adminSum = adminSum + int_itemVal; // adds together values as it iterates
             
             request.setAttribute("adminTotal",  adminSum); //sets price value to display on admin page
             
         }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); //loads login page
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //does nothing
    }

}
