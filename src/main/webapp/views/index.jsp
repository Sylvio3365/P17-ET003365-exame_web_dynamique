<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
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
        
        .content-container {
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
            margin-bottom: 15px;
            font-size: 24px;
            font-weight: 600;
        }
        
        h1:nth-child(2) {
            color: #666;
            font-size: 16px;
            margin-bottom: 30px;
        }
        
        .links-container {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }
        
        a {
            display: inline-block;
            padding: 12px 24px;
            color: white;
            text-decoration: none;
            font-weight: 500;
            border-radius: 6px;
            background: linear-gradient(to right, #667eea, #764ba2);
            transition: all 0.3s ease;
            min-width: 120px;
        }
        
        a:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        a:active {
            transform: translateY(0);
        }
        
        @media (max-width: 500px) {
            .links-container {
                flex-direction: column;
                gap: 15px;
            }
            
            a {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="content-container">
        <h1>Depenses - credit</h1>
        <h1>Examen web dynamique - ETU003365</h1>
        
        <div class="links-container">
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/credit">Dashboard</a>
        </div>
    </div>
</body>
</html>