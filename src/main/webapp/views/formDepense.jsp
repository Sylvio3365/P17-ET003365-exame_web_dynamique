<%@ page import="java.util.List" %>
<%@ page import="models.Credit" %>
<%@ page import="models.BaseModel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire de Depense</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #667eea, #764ba2);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .form-container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 500px;
        }
        
        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-size: 28px;
            font-weight: 600;
        }
        
        .input-group {
            margin-bottom: 20px;
        }
        
        .input-group label {
            display: block;
            margin-bottom: 8px;
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }
        
        .input-group select,
        .input-group input {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .input-group select:focus,
        .input-group input:focus {
            border-color: #764ba2;
            outline: none;
            box-shadow: 0 0 0 3px rgba(118, 75, 162, 0.2);
        }
        
        button {
            width: 100%;
            background: linear-gradient(to right, #667eea, #764ba2);
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.1s, box-shadow 0.3s;
            margin-top: 10px;
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        button:active {
            transform: translateY(0);
        }
        
        select {
            cursor: pointer;
        }
        
        .back-button {
            display: inline-block;
            background: #eee;
            color: #333;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 500;
            text-align: center;
            transition: background 0.3s;
            margin-top: 20px;
        }
        
        .back-button:hover {
            background: #ccc;
        }
        
        @media (max-width: 600px) {
            .form-container {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Depense</h2>
        <form action="${pageContext.request.contextPath}/depense" method="post">
            <div class="input-group">
                <label for="creditId">Credit :</label>
                <select id="creditId" name="idcredit" required>
                    <option value="">-- Selectionnez un credit --</option>
                    <%
                    List<BaseModel> credits = (List<BaseModel>) request.getAttribute("credits");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if (credits != null) {
                        for (BaseModel base : credits) {
                            Credit credit = (Credit) base;
                    %>
                    <option value="<%= credit.getId() %>">
                        <%= credit.getLibelle() %>
                        (Du <%= sdf.format((Date) credit.getDebut()) %>
                        au <%= sdf.format((Date) credit.getFin()) %>)
                    </option>
                    <%
                        }
                    }
                    %>
                </select>
            </div>
            <div class="input-group">
                <label for="montant">Montant :</label>
                <input type="number" id="montant" name="montant" step="0.01" min="0" required>
            </div>
            <div class="input-group">
                <label for="date">Date :</label>
                <input type="date" id="date" name="date" required>
            </div>
            <button type="submit">Enregistrer</button>
        </form>

        <!-- Lien de retour -->
        <a href="${pageContext.request.contextPath}/Accueil.jsp" class="back-button">Retour</a>
    </div>
</body>
</html>
