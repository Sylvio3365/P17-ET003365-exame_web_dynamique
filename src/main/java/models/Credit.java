package models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.DB;
import models.BaseModel;
import models.Depense;

public class Credit extends BaseModel {
    private String libelle;
    private Date debut;
    private Date fin;
    private double montant;

    public Credit(int id) {
        super(id, "credit");
    }

    public Credit(int id, String libelle, Date debut, Date fin, double montant) {
        super(id, "credit");
        this.libelle = libelle;
        this.debut = debut;
        this.fin = fin;
        this.montant = montant;
    }

    public static List<Map<String, Object>> listeCreditsAvecDetails() throws Exception {
        Connection conn = null;
        try {
            conn = DB.getConn();
            List<Map<String, Object>> resultats = new ArrayList<>();
            Credit credit = new Credit(0);
            List<BaseModel> credits = credit.findAll(conn);
            for (BaseModel baseModel : credits) {
                Credit c = (Credit) baseModel;
                double totalDepenses = c.totalDepenses(conn);
                double reste = c.reste(conn);

                Map<String, Object> details = new HashMap<>();
                details.put("credit", c);
                details.put("totalDepenses", totalDepenses);
                details.put("reste", reste);

                resultats.add(details);
            }

            return resultats;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            conn.close();
        }
    }

    // Version avec affichage formaté
    public static void afficherCreditsAvecDetails() throws Exception {
        List<Map<String, Object>> creditsDetails = listeCreditsAvecDetails();

        System.out.println("------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-10s | %-10s | %-10s | %-10s |%n",
                "ID", "Libellé", "Début", "Fin", "Dépenses", "Reste");
        System.out.println("------------------------------------------------------------------");

        for (Map<String, Object> detail : creditsDetails) {
            Credit c = (Credit) detail.get("credit");
            double totalDepenses = (double) detail.get("totalDepenses");
            double reste = (double) detail.get("reste");

            System.out.printf("| %-4d | %-20s | %-10s | %-10s | %-10.2f | %-10.2f |%n",
                    c.getId(),
                    c.getLibelle(),
                    c.getDebut(),
                    c.getFin(),
                    totalDepenses,
                    reste);
        }
        System.out.println("------------------------------------------------------------------");
    }

    public double totalDepenses(Connection conn) throws Exception {
        List<Depense> listeDepenses = Depense.findByIdCredit(conn, getId());
        double total = 0;
        for (Depense depense : listeDepenses) {
            total += depense.getMontant();
        }
        return total;
    }

    public double reste(Connection conn) throws Exception {
        return this.montant - this.totalDepenses(conn);
    }

    // Getters and Setters
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
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
        List<BaseModel> credits = new ArrayList<>();

        for (Map<String, Object> row : results) {
            credits.add(mapToCredit(row));
        }

        return credits;
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
            return mapToCredit(result);
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
        data.put("libelle", libelle);
        data.put("debut", debut);
        data.put("fin", fin);
        data.put("montant", montant);

        if (getId() == 0) { // Nouveau crédit
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
            throw new Exception("Cannot update a credit with ID 0");
        }

        Map<String, Object> data = new HashMap<>();
        if (libelle != null)
            data.put("libelle", libelle);
        if (debut != null)
            data.put("debut", debut);
        if (fin != null)
            data.put("fin", fin);
        if (montant != 0)
            data.put("montant", montant);

        if (!data.isEmpty()) {
            DB.update(conn, getTableName(), getId(), data);
        }
    }

    private Credit mapToCredit(Map<String, Object> map) {
        return new Credit(
                (int) map.get("id"),
                (String) map.get("libelle"),
                (Date) map.get("debut"),
                (Date) map.get("fin"),
                ((BigDecimal) map.get("montant")).doubleValue()); // Correction ici
    }

    @Override
    public String toString() {
        return "Credit [id=" + getId() + ", libelle=" + libelle +
                ", debut=" + debut + ", fin=" + fin +
                ", montant=" + montant + "]";
    }
}