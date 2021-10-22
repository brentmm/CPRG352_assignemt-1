package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");
        String userTotal = (String) session.getAttribute("sessionPrice");

        //checks if username is null if true forces login
        if (username == null || username.equals("")) {
            response.sendRedirect("login"); //redirects to login page
            return;
        }

        if (username.equals("admin")) {
            response.sendRedirect("admin");
        } else {
            
            //read in user items cost

            request.setAttribute("username", username); //sets username to display on web page
            request.setAttribute("totalPrice", userTotal); //sets username to display on web page
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
            return;
        }
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
            request.setAttribute("category", category);//setting values of textboxes
            request.setAttribute("item", itemName);
            request.setAttribute("price", itemPrice);
            request.setAttribute("error", "Invalid. Please re-enter");
            request.setAttribute("totalPrice", userTotal);
            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }

        int intPrice = Integer.parseInt(itemPrice);

        if (intPrice < 0 || intPrice > 10000) {
            request.setAttribute("username", username); //sets username to display on web page
            request.setAttribute("category", category);//setting values of textboxes
            request.setAttribute("item", itemName);
            request.setAttribute("price", itemPrice);
            request.setAttribute("error", "Invalid. Please re-enter");
            request.setAttribute("totalPrice", userTotal);
            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }

        int intTotal = Integer.parseInt(userTotal);
//        request.setAttribute("error", intTotal + "+" + intPrice); //setting message to notify user of added item

        intTotal += intPrice;

        request.setAttribute("message", "The item was sucessfully added to your inventory."); //setting message to notify user of added item
        request.setAttribute("totalPrice", intTotal);

        String s_intTotal = Integer.toString(intTotal);

        request.setAttribute("username", username); //sets username to display on web page
        session.setAttribute("sessionPrice", s_intTotal);

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
        return;
    }

}
