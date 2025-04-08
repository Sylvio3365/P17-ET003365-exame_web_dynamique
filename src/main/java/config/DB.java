package config;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
    public static final String DB_URL = "jdbc:mysql://172.80.237.53:3306/db_s2_ETU003365";
    public static final String DB_USER = "ETU003365";
    public static final String DB_PASSWORD = "FnVGonkK";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // public static final String DB_URL = "jdbc:mysql://localhost:3306/prepa";
    // public static final String DB_USER = "root";
    // public static final String DB_PASSWORD = "";
    // public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConn() throws Exception {
        try {
            Class.forName(DB_DRIVER);
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    /**
     * Récupère tous les enregistrements d'une table
     * 
     * @param conn      Connection à la base de données
     * @param tableName Nom de la table
     * @return Liste de Map représentant les enregistrements
     * @throws SQLException
     */
    public static List<Map<String, Object>> findAll(Connection conn, String tableName) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        }
        return results;
    }

    /**
     * Récupère un enregistrement par son ID
     * 
     * @param conn      Connection à la base de données
     * @param tableName Nom de la table
     * @param id        ID de l'enregistrement
     * @return Map représentant l'enregistrement ou null si non trouvé
     * @throws SQLException
     */
    public static Map<String, Object> findById(Connection conn, String tableName, int id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        Map<String, Object> result = new HashMap<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            // System.out.println(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        result.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    return result;
                }
            }
        }
        // System.out.println(result);
        return null;
    }

    /**
     * Insère un nouvel enregistrement
     * 
     * @param conn      Connection à la base de données
     * @param tableName Nom de la table
     * @param data      Map contenant les données à insérer
     * @return ID du nouvel enregistrement ou -1 en cas d'échec
     * @throws SQLException
     */
    public static int save(Connection conn, String tableName, Map<String, Object> data) throws SQLException {
        if (data.isEmpty())
            return -1;

        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        List<Object> values = new ArrayList<>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (columns.length() > 0) {
                columns.append(", ");
                placeholders.append(", ");
            }
            columns.append(entry.getKey());
            placeholders.append("?");
            values.add(entry.getValue());
        }

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Met à jour un enregistrement
     * 
     * @param conn      Connection à la base de données
     * @param tableName Nom de la table
     * @param id        ID de l'enregistrement à mettre à jour
     * @param data      Map contenant les données à mettre à jour
     * @return Nombre de lignes affectées
     * @throws SQLException
     */
    public static int update(Connection conn, String tableName, int id, Map<String, Object> data) throws SQLException {
        if (data.isEmpty())
            return 0;

        StringBuilder updates = new StringBuilder();
        List<Object> values = new ArrayList<>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (updates.length() > 0) {
                updates.append(", ");
            }
            updates.append(entry.getKey()).append(" = ?");
            values.add(entry.getValue());
        }

        values.add(id); // Pour le WHERE id = ?
        // System.out.println(values);
        String sql = "UPDATE " + tableName + " SET " + updates + " WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }
            return pstmt.executeUpdate();
        }
    }

    /**
     * Supprime un enregistrement
     * 
     * @param conn      Connection à la base de données
     * @param tableName Nom de la table
     * @param id        ID de l'enregistrement à supprimer
     * @return Nombre de lignes affectées
     * @throws SQLException
     */
    public static int delete(Connection conn, String tableName, int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

}