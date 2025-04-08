<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
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
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .dashboard-container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 500px;
            text-align: center;
        }

        h1 {
            color: #333;
            margin-bottom: 30px;
            font-size: 28px;
            font-weight: 600;
        }

        .links-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 20px;
        }

        a {
            display: block;
            padding: 14px 20px;
            color: white;
            text-decoration: none;
            font-weight: 500;
            border-radius: 6px;
            transition: all 0.3s ease;
        }

        a:not(.home-link) {
            background: linear-gradient(to right, #667eea, #764ba2);
        }

        a.home-link {
            background:rgb(116, 116, 116); /* vert dégradé */
        }

        a:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        a:active {
            transform: translateY(0);
        }

        @media (max-width: 500px) {
            .dashboard-container {
                padding: 30px 20px;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <h1>Depenses - Credit</h1>
        <div class="links-container">
            <a href="/ETU003365/formCredit">Ajouter credit</a>
            <a href="/ETU003365/formDepense">Ajouter depense</a>

            <a href="/ETU003365/" class="home-link">Accueil</a>
        </div>
    </div>
</body>
</html>
