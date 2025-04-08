create database if not exists prepa;

-- Table des utilisateurs
CREATE TABLE
    IF NOT EXISTS User (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


INSERT INTO User (name, email, password) 
VALUES ('user1', 'user1@example.com', 'mdp');

-- Table des crédits
CREATE TABLE
    IF NOT EXISTS credit (
        id INT PRIMARY KEY AUTO_INCREMENT,
        libelle VARCHAR(255) NOT NULL,
        debut DATE NOT NULL,
        fin DATE NOT NULL,
        montant DECIMAL(10, 2) NOT NULL
    );

-- Table des dépenses liées à un crédit
CREATE TABLE
    IF NOT EXISTS depense (
        id INT PRIMARY KEY AUTO_INCREMENT,
        idcredit INT NOT NULL,
        montant DECIMAL(10, 2) NOT NULL,
        date DATE NOT NULL,
        FOREIGN KEY (idCredit) REFERENCES credit (id)
    );