
package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;


public class userServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService ns = new UserService();
        
          try {           
            List<User> users = ns.getAll();
            request.setAttribute("users", users);
            System.out.println(users);
        } catch (Exception ex) {
            System.out.println("Error loading Users");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/Users.jsp").forward(request, response);
        return;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        
        String toDelete = request.getParameter("toDelete");
        String toUpdate = request.getParameter("toUpdate");
        
        String email = request.getParameter("email");
        boolean active = true;
        String first_name = request.getParameter("firstName");
        String last_name = request.getParameter("lastName");
        String password = request.getParameter("password");
        int role = 0;
        


        UserService us = new UserService();
        
        
        try {
            switch (action) {
                case "add":
                    role = Integer.parseInt(request.getParameter("roleSelect"));
                    System.out.println("in case add");
                    us.insert(email, active, first_name, last_name, password, role);
                    System.out.println("added new user");
                    break;
                case "delete":
                    us.delete(toDelete);
                    break;
                case "update":
                    User toUpdateUser = us.get(toUpdate);
                    request.setAttribute("updatedEmail", toUpdateUser.getEmail());
                    request.setAttribute("updatedFirstName", toUpdateUser.getFirst_name());
                    request.setAttribute("updatedLastName", toUpdateUser.getLast_name());
                    request.setAttribute("updatedPassword", toUpdateUser.getPassword());
                    request.setAttribute("updatedRole", toUpdateUser.getRole());
                    break;
 
                default: 
                    getServletContext().getRequestDispatcher("/WEB-INF/Users.jsp").forward(request, response);

            }
        } catch (Exception ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        try {           
            List<User> users = us.getAll();
            request.setAttribute("users", users);
            System.out.println(users);
        } catch (Exception ex) {
            System.out.println("Error loading Users");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/Users.jsp").forward(request, response);

    }

}
