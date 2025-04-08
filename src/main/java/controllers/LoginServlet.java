package controllers;

import java.io.IOException;
import models.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/ETU003365/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("name");
        String password = req.getParameter("password");
        try {
            if (User.isLoginCorrecte(username, password)) {
                req.getSession().setAttribute("user", username);
                resp.sendRedirect("/ETU003365/Accueil.jsp");
            } else {
                resp.sendRedirect("/ETU003365/login.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}
