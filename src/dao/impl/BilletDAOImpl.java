package dao.impl;

import dao.BilletDAO;
import metier.Billet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BilletDAOImpl implements BilletDAO {
    private final Connection connection;

    // Constructeur avec connexion
    public BilletDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createBillet(Billet billet) throws Exception {
        String query = "INSERT INTO billet (code_qr, date_emission, prix, statut, participant_id, evenement_id, tickets_are_generated) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, billet.getCode());  // Le code QR du billet
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));  // Date d'émission actuelle
            pstmt.setFloat(3, billet.getPrix());   // Prix du billet
            pstmt.setString(4, billet.getStatut()); // Statut initial du billet (ex : "disponible")
            
            // Si le billet est généré sans participant, on laisse le participant_id à 0
            pstmt.setInt(5, billet.getParticipantId()); // Participant ID (0 si non attribué)
            
            pstmt.setInt(6, billet.getEvenementId());  // ID de l'événement pour lequel le billet est créé
            pstmt.setBoolean(7, billet.isTicketAreGenerated()); // Définir si les billets sont générés (true/false)
            
            pstmt.executeUpdate();  // Exécution de l'insertion dans la base
        }
    }
    
    
/*

    @Override
    public Billet getBilletById(int id) throws Exception {
        String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre AS eventTitle, p.nom AS participantName " +
                       "FROM billet b " +
                       "JOIN evenement e ON b.evenement_id = e.id " +
                       "JOIN utilisateur p ON b.participant_id = p.id " +
                       "WHERE b.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Billet(
                        rs.getInt("id"),
                        rs.getString("code_qr"),
                        rs.getString("eventTitle"),
                        rs.getString("participantName"),
                        rs.getFloat("prix"),           // Ajout du prix
                        rs.getString("statut"),        // Ajout du statut
                        rs.getInt("participant_id"),   // Ajout de participantId
                        rs.getInt("evenement_id")      // Ajout de evenementId
                    );
                }
            }
        }
        return null; // Aucun billet trouvé
    }

    @Override
    public List<Billet> getAllBillets() throws Exception {
        List<Billet> billets = new ArrayList<>();
        String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre AS eventTitle, p.nom AS participantName " +
                       "FROM billet b " +
                       "JOIN evenement e ON b.evenement_id = e.id " +
                       "JOIN utilisateur p ON b.participant_id = p.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                billets.add(new Billet(
                    rs.getInt("id"),
                    rs.getString("code_qr"),
                    rs.getString("eventTitle"),
                    rs.getString("participantName"),
                    rs.getFloat("prix"),           // Ajout du prix
                    rs.getString("statut"),        // Ajout du statut
                    rs.getInt("participant_id"),   // Ajout de participantId
                    rs.getInt("evenement_id")      // Ajout de evenementId
                ));
            }
        }
        return billets;
    }

    @Override
    public void updateBillet(Billet billet) throws Exception {
        String query = "UPDATE billet SET code_qr = ?, prix = ?, statut = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, billet.getCode());
            pstmt.setFloat(2, billet.getPrix());    // Mise à jour du prix
            pstmt.setString(3, billet.getStatut()); // Mise à jour du statut
            pstmt.setInt(4, billet.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBillet(int id) throws Exception {
        String query = "DELETE FROM billet WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Billet> getTicketsForParticipant(int participantId) throws SQLException {
        List<Billet> tickets = new ArrayList<>();
        String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre AS eventTitle, p.nom AS participantName " +
                       "FROM billet b " +
                       "JOIN evenement e ON b.evenement_id = e.id " +
                       "JOIN utilisateur p ON b.participant_id = p.id " +
                       "WHERE b.participant_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, participantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Billet(
                        rs.getInt("id"),
                        rs.getString("code_qr"),
                        rs.getString("eventTitle"),
                        rs.getString("participantName"),
                        rs.getFloat("prix"),           // Ajout du prix
                        rs.getString("statut"),        // Ajout du statut
                        rs.getInt("participant_id"),   // Ajout de participantId
                        rs.getInt("evenement_id")      // Ajout de evenementId
                    ));
                }
            }
        }
        return tickets;
    }*/
}
