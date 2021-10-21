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
//       double totalPrice = (double) session.getAttribute("sessionPrice");
        
        

        //checks if username is null if true forces login
        if (username == null || username.equals("")) {
            response.sendRedirect("login"); //redirects to login page
            return;
        }
        
        request.setAttribute("username", username ); //sets username to display on web page
//        request.setAttribute("totalPrice", totalPrice ); //sets uprice to display on web page
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        //gets session
        HttpSession session = request.getSession();
        
        double totalPrice = (double) session.getAttribute("sessionUser"); //grabing session variable
                
        String category = request.getParameter("category"); //fills login page input boxes
        String itemName  = request.getParameter("item");
        String itemPrice = request.getParameter("price");
        
        double price = Double.parseDouble(itemPrice);       
        
        totalPrice = totalPrice+ price;//updating total price to be displayed
         
       
        
        request.setAttribute("message", "The item was sucessfully added to your incentory."); //setting message to notify user of added item
        request.setAttribute("totalPrice", totalPrice);
    }

}
