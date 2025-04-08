package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BaseModel;
import models.Credit;

public class FormDepenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Credit c = new Credit(0);            
            List<BaseModel> credits = c.findAll();
            List <Credit> temp = new ArrayList<>();
            for (BaseModel credit : credits) {
                Credit cr = (Credit) credit;
                temp.add(cr);
            }
            req.setAttribute("credits", temp);
            req.getRequestDispatcher("formDepense.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}