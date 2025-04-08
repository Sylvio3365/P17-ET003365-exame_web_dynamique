package controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Credit;

public class CreditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String libelle = req.getParameter("libelle");
        String strDebut = req.getParameter("debut");
        String strFin = req.getParameter("fin");
        String strMontant = req.getParameter("montant");

        if (req.getSession().getAttribute("user") != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date debut;
            try {
                debut = new Date(dateFormat.parse(strDebut).getTime());
            } catch (ParseException e) {
                throw new ServletException(e.getMessage());
            }
            Date fin;
            try {
                fin = new Date(dateFormat.parse(strFin).getTime());
            } catch (ParseException e) {
                throw new ServletException(e.getMessage());
            }

            double montant = Double.parseDouble(strMontant);

            Credit credit = new Credit(0, libelle, debut, fin, montant);
            try {
                credit.save(); // Save the credit object

                // Success response with HTML
                resp.setContentType("text/html");
                resp.getWriter().println("<html><body>");
                resp.getWriter().println("<h2>Credit successfully added!</h2>");
                resp.getWriter().println("<p><a href=\"/ETU003365/formCredit.jsp\">Retour</a></p>");
                resp.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new ServletException(e.getMessage());
            }
        } else {
            resp.sendRedirect("/ETU003365/login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String tri = req.getParameter("tri");
            String dateDebut = req.getParameter("dateDebut");
            String dateFin = req.getParameter("dateFin");

            List<Map<String, Object>> credits = Credit.listeCreditsAvecDetails();

            // Filtrage par date si les paramètres sont présents
            if (dateDebut != null && !dateDebut.isEmpty() || dateFin != null && !dateFin.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // Conversion en java.sql.Date pour la comparaison
                java.sql.Date debut = dateDebut != null && !dateDebut.isEmpty()
                        ? new java.sql.Date(sdf.parse(dateDebut).getTime())
                        : null;
                java.sql.Date fin = dateFin != null && !dateFin.isEmpty()
                        ? new java.sql.Date(sdf.parse(dateFin).getTime())
                        : null;

                credits = credits.stream()
                        .filter(c -> {
                            Credit credit = (Credit) c.get("credit");
                            // Conversion des dates du crédit en java.sql.Date si nécessaire
                            java.sql.Date creditDebut = new java.sql.Date(credit.getDebut().getTime());
                            java.sql.Date creditFin = new java.sql.Date(credit.getFin().getTime());

                            boolean matches = true;
                            if (debut != null) {
                                matches = creditDebut.equals(debut) || creditDebut.after(debut);
                            }
                            if (fin != null && matches) {
                                matches = creditFin.equals(fin) || creditFin.before(fin);
                            }
                            return matches;
                        })
                        .collect(Collectors.toList());
            }

            // Tri comme avant
            if (tri != null) {
                credits.sort((a, b) -> {
                    Credit ca = (Credit) a.get("credit");
                    Credit cb = (Credit) b.get("credit");

                    switch (tri) {
                        case "montant":
                            return Double.compare(ca.getMontant(), cb.getMontant());
                        case "debut":
                            return ca.getDebut().compareTo(cb.getDebut());
                        case "fin":
                            return ca.getFin().compareTo(cb.getFin());
                        case "libelle":
                            return ca.getLibelle().compareToIgnoreCase(cb.getLibelle());
                        case "totalDepenses":
                            return Double.compare((Double) a.get("totalDepenses"), (Double) b.get("totalDepenses"));
                        case "reste":
                            return Double.compare((Double) a.get("reste"), (Double) b.get("reste"));
                        default:
                            return 0;
                    }
                });
            }

            req.setAttribute("credits", credits);
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement: " + e.getMessage(), e);
        }
    }

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
    // ServletException, IOException {
    // try {
    // String tri = req.getParameter("tri"); // tri = montant, debut, reste, etc.
    // List<Map<String, Object>> credits = Credit.listeCreditsAvecDetails();

    // if (tri != null) {
    // credits.sort((a, b) -> {
    // Credit ca = (Credit) a.get("credit");
    // Credit cb = (Credit) b.get("credit");

    // switch (tri) {
    // case "montant":
    // return Double.compare(ca.getMontant(), cb.getMontant());
    // case "debut":
    // return ca.getDebut().compareTo(cb.getDebut());
    // case "fin":
    // return ca.getFin().compareTo(cb.getFin());
    // case "libelle":
    // return ca.getLibelle().compareToIgnoreCase(cb.getLibelle());
    // case "totalDepenses":
    // return Double.compare((Double) a.get("totalDepenses"), (Double)
    // b.get("totalDepenses"));
    // case "reste":
    // return Double.compare((Double) a.get("reste"), (Double) b.get("reste"));
    // default:
    // return 0;
    // }
    // });
    // }

    // req.setAttribute("credits", credits);
    // req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    // } catch (Exception e) {
    // throw new ServletException(e.getMessage());
    // }
    // }
}