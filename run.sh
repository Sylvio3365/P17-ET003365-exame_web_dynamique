#!/bin/bash

# Variables
TOMCAT_HOME="/opt/tomcat10"
BUILD_DIR="./build"
SRC_DIR="./src"
WEBAPP_DIR="./webapp"
WAR_NAME="ETU003365.war"
JAVA_FILES_LIST="java_files.txt"

# 1. Créer le dossier /build
rm -rf "$BUILD_DIR"
echo "Création du répertoire $BUILD_DIR..."
mkdir -p "$BUILD_DIR/WEB-INF/classes"
mkdir -p "$BUILD_DIR/META-INF"
mkdir -p "$BUILD_DIR/static"

# 2. Ajouter le fichier web.xml dans build
if [ -f "src/main/$WEBAPP_DIR/WEB-INF/web.xml" ]; then
    echo "Ajout du fichier web.xml..."
    cp "src/main/$WEBAPP_DIR/WEB-INF/web.xml" "$BUILD_DIR/WEB-INF/"
    if [ $? -ne 0 ]; then
        echo "Erreur lors de la copie de web.xml."
        exit 1
    fi
else
    echo "Erreur : fichier web.xml introuvable dans src/main/$WEBAPP_DIR/WEB-INF/web.xml"
    exit 1
fi

# 3. Vérifier et copier les fichiers HTML et JSP
if [ -d "src/main/$WEBAPP_DIR/views" ]; then
    if find "src/main/$WEBAPP_DIR/views" -type f \( -name "*.html" -o -name "*.jsp" \) | grep -q .; then
        echo "Ajout des fichiers HTML et JSP..."
        cp -r "src/main/$WEBAPP_DIR/views/"* "$BUILD_DIR/"
        if [ $? -ne 0 ]; then
            echo "Erreur lors de la copie des fichiers HTML et JSP."
            exit 1
        fi
    else
        echo "Aucun fichier HTML ou JSP trouvé, passage à l'étape suivante."
    fi
else
    echo "Erreur : Répertoire src/main/$WEBAPP_DIR/views introuvable."
    exit 1
fi

# 4. Trouver les fichiers .java et les compiler
echo "Recherche des fichiers .java dans $SRC_DIR..."
find "$SRC_DIR" -name "*.java" > "$JAVA_FILES_LIST"
if [ ! -s "$JAVA_FILES_LIST" ]; then
    echo "Aucun fichier .java trouvé dans $SRC_DIR."
    exit 1
fi

echo "Compilation des fichiers Java listés dans $JAVA_FILES_LIST..."
# javac -d "$BUILD_DIR/WEB-INF/classes" -cp "lib/servlet-api.jar" @"$JAVA_FILES_LIST"
javac -d "$BUILD_DIR/WEB-INF/classes" -cp "lib/servlet-api.jar:lib/mysql-connector-j-9.2.0.jar" @"$JAVA_FILES_LIST"
if [ $? -ne 0 ]; then
    echo "Erreur lors de la compilation des fichiers Java."
    exit 1
fi
rm -f "$JAVA_FILES_LIST" # Nettoyer le fichier temporaire

# Copie du driver PostgreSQL dans WEB-INF/lib
echo "Ajout du driver Mysql..."
mkdir -p "$BUILD_DIR/WEB-INF/lib/"
cp "lib/mysql-connector-j-9.2.0.jar" "$BUILD_DIR/WEB-INF/lib/"

# 5. Créer le fichier WAR
echo "Création du fichier WAR..."
cd "$BUILD_DIR" || exit
jar -cvf "../$WAR_NAME" .
cd - || exit

# 6. Déployer le WAR dans Tomcat
echo "Déploiement du fichier WAR dans Tomcat..."
cp "$WAR_NAME" "$TOMCAT_HOME/webapps/"

# 7. Donner les permissions complètes
echo "Configuration des permissions complètes pour le fichier WAR..."
sudo chmod 777 "$TOMCAT_HOME/webapps/$WAR_NAME"
sudo chmod 770 "$TOMCAT_HOME/webapps/$(basename "$WAR_NAME" .war)"

if [ $? -eq 0 ]; then
    echo "Permissions complètes configurées avec succès pour $WAR_NAME."
else
    echo "Erreur lors de la configuration des permissions pour $WAR_NAME."
    exit 1
fi

# 8. Démarrer Tomcat
echo "Démarrage de Tomcat..."
sh "$TOMCAT_HOME/bin/startup.sh"

echo "Déploiement terminé. Accédez à l'application à : http://localhost:8080/$(basename "$WAR_NAME" .war)/"
