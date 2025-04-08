package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Depense;

import java.io.IOException;
import java.sql.Date;

public class DepenseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {

            try {
                int idcredit = Integer.parseInt(req.getParameter("idcredit"));
                double montant = Double.parseDouble(req.getParameter("montant"));
                Date date = Date.valueOf(req.getParameter("date")); // yyyy-MM-dd

                Depense depense = new Depense(0, idcredit, montant, date);

                depense.ajouter(); // Save the expense

                // Success response with HTML
                resp.setContentType("text/html");
                resp.getWriter().println("<html><body>");
                resp.getWriter().println("<h2>Dépense ajoutée avec succès!</h2>");
                resp.getWriter().println("<p><a href=\"/ETU003365/formDepense\">Retour</a></p>");
                resp.getWriter().println("</body></html>");

            } catch (Exception e) {
                throw new ServletException("Erreur lors de l'ajout de la dépense : " + e.getMessage());
            }
        } else {
            resp.sendRedirect("/ETU003365/login.jsp");
        }
    }

}
