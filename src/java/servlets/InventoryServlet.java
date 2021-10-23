package servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.HomeItem;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");

        //checks if username is null if true forces login
        if (username == null || username.equals("")) {
            response.sendRedirect("login"); //redirects to login page
            return;
        }

        //read in user items cost
        ArrayList<HomeItem> userItems = new ArrayList<>(); //array to store objects

        String path = getServletContext().getRealPath("/WEB-INF/homeitems.txt");// file path

        BufferedReader br = new BufferedReader(new FileReader(new File(path)));// creating reader

        String temp;

        while ((temp = br.readLine()) != null) { //loops thorough reading in lines from file
//            System.out.println(temp);
            String[] itemSections = temp.split(",");//splits lines by commas

            HomeItem userCost = new HomeItem(itemSections[0], itemSections[1], itemSections[2], itemSections[3]); // insets the values into an object

            userItems.add(userCost); //adds the object to an array
        }

        br.close();

        int int_userSum = 0;

        for (int n = 0; n < userItems.size(); n++) { //loops though an array of objects
            if (userItems.get(n).getUser().equals(username)) { //checks if object user is current user
                String itemVal = userItems.get(n).getPrice(); //gets all price values for current user
//                System.out.println(s_itemVal);

                int int_itemVal = Integer.parseInt(itemVal); //swaps string value of price into an integer

                int_userSum = int_userSum + int_itemVal; //adds together values as it iterates

            }
        }

        String userSum = Integer.toString(int_userSum); //swaps total price value back to a string
        session.setAttribute("sessionPrice", userSum); //sets session price value

//        System.out.println(userSum);
        request.setAttribute("username", username); //sets username to display on web page
        request.setAttribute("userTotal", userSum); //sets price to display on web page

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");
        String userTotal = (String) session.getAttribute("sessionPrice");

        String category = request.getParameter("category");
        String itemName = request.getParameter("item");
        String itemPrice = request.getParameter("price");

        if (itemName == null || itemName.equals("") || itemName.equals("null") || itemPrice == null || itemPrice.equals("") || !itemPrice.matches("[0-9]+")) { //checking user entered valid price
            request.setAttribute("username", username); //sets username to display on web page
            request.setAttribute("usercategory", category);//setting values of input boxes
            request.setAttribute("userItem", itemName);
            request.setAttribute("userPrice", itemPrice);

            request.setAttribute("error", "Invalid. Please re-enter");//sets error message

            request.setAttribute("userTotal", userTotal); //sets user total on webpage

            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }

        int intPrice = Integer.parseInt(itemPrice); //swaps item price value into an integer

        if (intPrice < 1 || intPrice > 10000) {
            request.setAttribute("username", username); //sets username to display on web page
            request.setAttribute("userCategory", category);//setting values of input boxes
            request.setAttribute("userItem", itemName);
            request.setAttribute("userPrice", itemPrice);

            request.setAttribute("error", "Invalid. Please re-enter");//sets error message

            request.setAttribute("userTotal", userTotal);//sets user total on webpage

            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }

        //write item to file
        String path = getServletContext().getRealPath("/WEB-INF/homeitems.txt");//file path

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));//creatinf writer that can append
        pw.append(username + "," + category + "," + itemName + "," + itemPrice + "\n"); //appends all user values into the file and moves down a line
        pw.close();

        int int_userTotal = Integer.parseInt(userTotal); //swaps userTotal value into an integer

//        request.setAttribute("error", intTotal + "+" + intPrice); //setting message to notify user of added item
        int_userTotal += intPrice;//adds items price to users total price
        
        userTotal = Integer.toString(int_userTotal);
        
        session.setAttribute("sessionPrice", userTotal);//setting session value of users total

        request.setAttribute("message", "The item was sucessfully added to your inventory."); //setting message to notify user of added item
        request.setAttribute("username", username); //sets username to display on web page      
        request.setAttribute("userTotal", int_userTotal); //sets new total price on screen

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
        return;
    }

}
