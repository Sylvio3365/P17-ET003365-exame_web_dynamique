<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, models.Credit, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord - Crédits</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #667eea, #764ba2);
            min-height: 100vh;
            padding: 30px 20px;
        }
        
        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px;
            position: relative;
        }
        
        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
            font-size: 28px;
            font-weight: 600;
        }
        
        .filter-section {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
            flex-wrap: wrap;
            gap: 20px;
        }
        
        .filter-form {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
        }
        
        .filter-form label {
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }
        
        .filter-form select, 
        .filter-form input[type="date"] {
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
        }
        
        .filter-form select:focus,
        .filter-form input[type="date"]:focus {
            border-color: #764ba2;
            outline: none;
            box-shadow: 0 0 0 3px rgba(118, 75, 162, 0.2);
        }
        
        .filter-form button {
            background: linear-gradient(to right, #667eea, #764ba2);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.1s, box-shadow 0.3s;
        }
        
        .filter-form button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            overflow: hidden;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        thead {
            background: linear-gradient(to right, #667eea, #764ba2);
            color: white;
        }
        
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        th {
            font-weight: 500;
            letter-spacing: 0.5px;
        }
        
        tbody tr:hover {
            background-color: rgba(118, 75, 162, 0.05);
        }
        
        tbody tr:last-child td {
            border-bottom: none;
        }
        
        .text-center {
            text-align: center;
        }

        .btn-retour-container {
            margin-top: 30px;
            text-align: center;
        }
        
        .btn-retour {
            display: inline-block;
            background: linear-gradient(to right, #6c757d, #495057);
            color: white;
            text-decoration: none;
            padding: 12px 25px;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border: none;
        }
        
        .btn-retour:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            background: linear-gradient(to right, #5a6268, #3a4249);
        }
        
        @media (max-width: 768px) {
            .container {
                padding: 20px 15px;
                width: 95%;
            }
            
            .filter-section {
                flex-direction: column;
            }
            
            .filter-form {
                width: 100%;
            }
            
            table {
                display: block;
                overflow-x: auto;
                white-space: nowrap;
            }
            
            th, td {
                padding: 8px 10px;
            }
            
            .filter-form select, 
            .filter-form input[type="date"],
            .filter-form button {
                width: 100%;
            }

            .btn-retour {
                width: 100%;
                padding: 12px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Tableau de bord - Liste des crédits</h2>
        
        <div class="filter-section">
            <!-- Formulaire de tri -->
            <form method="get" action="${pageContext.request.contextPath}/credit" class="filter-form">
                <label for="tri">Trier(croissant) par :</label>
                <select name="tri" id="tri">
                    <option value="">-- Sélectionner --</option>
                    <option value="libelle" <%= request.getParameter("tri") != null && request.getParameter("tri").equals("libelle") ? "selected" : "" %>>Libellé</option>
                    <option value="debut" <%= request.getParameter("tri") != null && request.getParameter("tri").equals("debut") ? "selected" : "" %>>Date début</option>
                    <option value="fin" <%= request.getParameter("tri") != null && request.getParameter("tri").equals("fin") ? "selected" : "" %>>Date fin</option>
                    <option value="montant" <%= request.getParameter("tri") != null && request.getParameter("tri").equals("montant") ? "selected" : "" %>>Montant</option>
                    <option value="totalDepenses" <%= request.getParameter("tri") != null && request.getParameter("tri").equals("totalDepenses") ? "selected" : "" %>>Total Dépenses</option>
                    <option value="reste" <%= request.getParameter("tri") != null && request.getParameter("tri").equals("reste") ? "selected" : "" %>>Reste</option>
                </select>
                <button type="submit">Appliquer</button>
            </form>
            
            <!-- Formulaire de filtrage par date -->
            <form method="get" action="${pageContext.request.contextPath}/credit" class="filter-form">
                <label for="dateDebut">De :</label>
                <input type="date" name="dateDebut" id="dateDebut" value="<%= request.getParameter("dateDebut") != null ? request.getParameter("dateDebut") : "" %>">
                
                <label for="dateFin">À :</label>
                <input type="date" name="dateFin" id="dateFin" value="<%= request.getParameter("dateFin") != null ? request.getParameter("dateFin") : "" %>">
                
                <input type="hidden" name="tri" value="<%= request.getParameter("tri") != null ? request.getParameter("tri") : "" %>">
                <button type="submit">Filtrer</button>
            </form>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Libellé</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Montant</th>
                    <th>Total Dépenses</th>
                    <th>Reste</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Map<String, Object>> credits = (List<Map<String, Object>>) request.getAttribute("credits");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                if (credits != null && !credits.isEmpty()) {
                    for (Map<String, Object> creditDetail : credits) {
                        Credit credit = (Credit) creditDetail.get("credit");
                        double totalDepenses = (Double) creditDetail.get("totalDepenses");
                        double reste = (Double) creditDetail.get("reste");
                %>
                <tr>
                    <td><%= credit.getId() %></td>
                    <td><%= credit.getLibelle() %></td>
                    <td><%= dateFormat.format(credit.getDebut()) %></td>
                    <td><%= dateFormat.format(credit.getFin()) %></td>
                    <td><%= String.format("%,.2f", credit.getMontant()) %> Ar</td>
                    <td><%= String.format("%,.2f", totalDepenses) %> Ar</td>
                    <td><%= String.format("%,.2f", reste) %> Ar</td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7" class="text-center">Aucun crédit trouvé.</td>
                </tr>
                <% } %>
            </tbody>
        </table>

        <div class="btn-retour-container">
            <a href="/ETU003365/" class="btn-retour">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>