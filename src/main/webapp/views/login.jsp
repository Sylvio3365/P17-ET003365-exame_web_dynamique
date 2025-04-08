<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
        }
        
        .login-container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            width: 350px;
        }
        
        .login-container h3 {
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
        
        .helper-text {
            margin-top: 5px;
            font-size: 12px;
            color: #888;
        }
        
        .btn-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 20px;
        }
        
        .btn-retour {
            background: linear-gradient(to right, #6c757d, #495057);
            text-decoration: none;
            text-align: center;
            padding: 12px;
            border-radius: 6px;
            color: white;
            font-weight: 500;
            transition: transform 0.1s, box-shadow 0.3s;
        }
        
        .btn-retour:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        .btn-retour:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h3>Login</h3>
        <form action="/ETU003365/login" method="post">
            <div class="input-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="name" placeholder="user1" required>
                <div class="helper-text">user1</div>
            </div>
            
            <div class="input-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="mdp" required>
                <div class="helper-text">mdp</div>
            </div>
            
            <div class="btn-container">
                <button type="submit">Sign In</button>
                <a href="/ETU003365/" class="btn-retour">Retour</a>
            </div>
        </form>
        <p>user1</p>
        <p>mdp</p>
    </div>
</body>
</html>