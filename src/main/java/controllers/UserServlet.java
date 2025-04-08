package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        User u = new User(0, name, email, pass, new Timestamp(System.currentTimeMillis()));
        resp.getWriter().print(u);
    } // create + update

    //do get 
    //read + delete
}
