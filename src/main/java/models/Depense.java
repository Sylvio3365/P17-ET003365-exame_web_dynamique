package models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.DB;

public class Depense extends BaseModel {
    private int idcredit;
    private double montant;
    private Date date;

    public Depense(int id) {
        super(id, "depense");
    }

    public Depense(int id, int idcredit, double montant, Date date) {
        super(id, "depense");
        this.idcredit = idcredit;
        this.montant = montant;
        this.date = date;
    }

    public void ajouter() throws Exception {
        Connection conn = null;
        try {
            conn = DB.getConn();
            Credit c = new Credit(0);
            c = (Credit) c.findById(conn, this.getIdcredit());
            if (c.reste(conn) >= this.getMontant()) {
                this.save(conn);
            } else {
                throw new Exception(String.format(
                        "Solde insuffisant. Montant disponible: %.2f, Montant demandé: %.2f",
                        c.reste(conn),
                        this.montant));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    public static List<Depense> findByIdCredit(Connection conn, int idcredit) throws Exception {
        List<Depense> depenses = new ArrayList<>();
        Depense d = new Depense(0);
        List<BaseModel> all = d.findAll(conn);
        for (BaseModel depense : all) {
            if (((Depense) depense).getIdcredit() == idcredit) {
                depenses.add((Depense) depense);
            }
        }
        return depenses;
    }

    // Getters and Setters
    public int getIdcredit() {
        return idcredit;
    }

    public void setIdcredit(int idcredit) {
        this.idcredit = idcredit;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        List<BaseModel> depenses = new ArrayList<>();

        for (Map<String, Object> row : results) {
            depenses.add(mapToDepense(row));
        }

        return depenses;
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
            return mapToDepense(result);
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
        data.put("idcredit", idcredit);
        data.put("montant", montant);
        data.put("date", date);

        if (getId() == 0) { // Nouvelle dépense
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
            throw new Exception("Cannot update a depense with ID 0");
        }

        Map<String, Object> data = new HashMap<>();
        if (idcredit != 0)
            data.put("idcredit", idcredit);
        if (montant != 0)
            data.put("montant", montant);
        if (date != null)
            data.put("date", date);

        if (!data.isEmpty()) {
            DB.update(conn, getTableName(), getId(), data);
        }
    }

    private Depense mapToDepense(Map<String, Object> map) {
        return new Depense(
                (int) map.get("id"),
                (int) map.get("idcredit"),
                ((BigDecimal) map.get("montant")).doubleValue(),
                (Date) map.get("date"));
    }

    @Override
    public String toString() {
        return "Depense [id=" + getId() + ", idcredit=" + idcredit +
                ", montant=" + montant + ", date=" + date + "]";
    }
}