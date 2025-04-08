package models;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.DB;

public class User extends BaseModel {
    private String name;
    private String email;
    private String password;
    private Timestamp createdAt;

    public User(int id) {
        super(id, "User");
    }

    public User(int id, String name, String email, String password, Timestamp createdAt) {
        super(id, "User");
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void delete(Connection conn) throws Exception {
        DB.delete(conn, getTableName(), getId());
    }

    @Override
    public void delete() throws Exception {
        try (Connection conn = DB.getConn()) {
            delete(conn);
        }
    }

    @Override
    public List<BaseModel> findAll(Connection conn) throws Exception {
        List<Map<String, Object>> results = DB.findAll(conn, getTableName());
        List<BaseModel> users = new ArrayList<>();

        for (Map<String, Object> row : results) {
            users.add(mapToUser(row));
        }

        return users;
    }

    public User findByName(String name) throws Exception {
        User valiny = new User(0);
        List<BaseModel> all = valiny.findAll();
        for (BaseModel baseModel : all) {
            User temp = (User) baseModel;
            if (temp.getName().equals(name)) {
                valiny = temp;
            }
        }
        return valiny;
    }

    public static boolean isLoginCorrecte(String name, String pass) throws Exception {
        boolean valiny = false;
        User v = new User(0);
        List<BaseModel> all = v.findAll();
        for (BaseModel baseModel : all) {
            User temp = (User) baseModel;
            if (temp.getName().equals(name) && temp.getPassword().equals(pass)) {
                valiny = true;
            }
        }
        return valiny;
    }

    @Override
    public List<BaseModel> findAll() throws Exception {
        try (Connection conn = DB.getConn()) {
            return findAll(conn);
        }
    }

    @Override
    public BaseModel findById(Connection conn, int id) throws Exception {
        Map<String, Object> result = DB.findById(conn, getTableName(), id);
        if (result != null) {
            return mapToUser(result);
        }
        return null;
    }

    @Override
    public BaseModel findById(int id) throws Exception {
        try (Connection conn = DB.getConn()) {
            return findById(conn, id);
        }
    }

    @Override
    public void save(Connection conn) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("password", password);
        data.put("created_at", createdAt);

        if (getId() == 0) { // Nouvel utilisateur
            int newId = DB.save(conn, getTableName(), data);
            setId(newId);
        } else {
            update(conn);
        }
    }

    @Override
    public void save() throws Exception {
        try (Connection conn = DB.getConn()) {
            save(conn);
        }
    }

    @Override
    public void update() throws Exception {
        try (Connection conn = DB.getConn()) {
            update(conn);
        }
    }

    @Override
    public void update(Connection conn) throws Exception {
        if (getId() == 0) {
            throw new Exception("Cannot update a user with ID 0");
        }

        Map<String, Object> data = new HashMap<>();
        if (name != null)
            data.put("name", name);
        if (email != null)
            data.put("email", email);
        if (password != null)
            data.put("password", password);
        if (createdAt != null)
            data.put("created_at", createdAt);

        if (!data.isEmpty()) {
            DB.update(conn, getTableName(), getId(), data);
        }
    }

    // Méthode utilitaire pour convertir une Map en User
    private User mapToUser(Map<String, Object> map) {
        return new User(
                (int) map.get("id"),
                (String) map.get("name"),
                (String) map.get("email"),
                (String) map.get("password"),
                (Timestamp) map.get("created_at"));
    }

    // Méthode toString pour faciliter le débogage
    @Override
    public String toString() {
        return "User [id=" + getId() + ", name=" + name + ", email=" + email +
                ", password=" + password + ", createdAt=" + createdAt + "]";
    }
}