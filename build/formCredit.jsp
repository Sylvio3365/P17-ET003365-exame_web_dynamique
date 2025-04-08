<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Credit</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
        
        .input-group input {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
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
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        button:active {
            transform: translateY(0);
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
        <h2>Credit</h2>
        <form action="/ETU003365/credit" method="post">
            <div class="input-group">
                <label for="libelle">Libelle :</label>
                <input type="text" id="libelle" name="libelle" required>
            </div>
            <div class="input-group">
                <label for="debut">Date de debut :</label>
                <input type="date" id="debut" name="debut" required>
            </div>
            <div class="input-group">
                <label for="fin">Date de fin :</label>
                <input type="date" id="fin" name="fin" required>
            </div>
            <div class="input-group">
                <label for="montant">Montant :</label>
                <input type="number" id="montant" name="montant" step="0.01" required>
            </div>
            <div class="input-group">
                <button type="submit">Enregistrer</button>
            </div>
        </form>
        <a href="${pageContext.request.contextPath}/Accueil.jsp" class="back-button">Retour</a>
    </div>
</body>
</html>