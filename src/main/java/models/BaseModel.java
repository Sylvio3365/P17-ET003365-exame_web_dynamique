package models;

import java.sql.Connection;
import java.util.List;

public abstract class BaseModel {
    private int id;
    private String tableName;

    public BaseModel(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void save(Connection conn) throws Exception;

    public abstract void save() throws Exception;

    public abstract void delete(Connection conn) throws Exception;

    public abstract void delete() throws Exception;

    public abstract void update() throws Exception;

    public abstract void update(Connection conn) throws Exception;

    public abstract BaseModel findById(Connection conn, int id) throws Exception;

    public abstract BaseModel findById(int id) throws Exception;

    public abstract List<BaseModel> findAll(Connection conn) throws Exception;

    public abstract List<BaseModel> findAll() throws Exception;
}
