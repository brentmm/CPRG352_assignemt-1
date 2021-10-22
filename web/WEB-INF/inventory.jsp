<%-- 
    Document   : inventory
    Created on : Oct 12, 2021, 9:19:04 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory: Inventory</title>
    </head>
    <body>
        <h1>Home Inventory for ${username}</h1>
        <h2>Add Item</h2>
        <form method="POST">
            <label>Category:</label>
            <!--input for category-->
            <select name="category">
                <option value="bedroom">Bedroom</option>
                <option value="kitchen">Kitchen</option>
                <option value="living room">Living Room</option>
                <option value="garage">Garage</option>
            </select>
            <br>
            <label>Item Name:</label>
            <!--input for item-->
            <input type="text" name="item" value="${item}">
            <br>
            <label>Price:</label>
            <!--input for price-->
            <input type="text" name="price" value="${price}"> 
            <br>
            <br>
            <!--submit button to login-->
            <input type="submit" value="Add"> 
            ${error}
        </form>
        <p>${message}</p>
        
        <p>Total value in inventory: $${totalPrice}</p>
        
         <a href="login?logout">Log out</a> 
    </body>
</html>
