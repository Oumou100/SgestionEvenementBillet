package dao.impl;

import dao.InscriptionDAO;
import metier.Inscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAOImpl implements InscriptionDAO {
    private final Connection connection;

    // Constructeur avec connexion
    public InscriptionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createInscription(Inscription inscription) throws SQLException {
        String query = "INSERT INTO inscription (date_inscription, statut, participant_id, evenement_id) " +
                       "VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(inscription.getDateInscription().getTime()));
            pstmt.setString(2, inscription.getStatut());
            pstmt.setInt(3, inscription.getParticipantId());
            pstmt.setInt(4, inscription.getEvenementId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Inscription getInscriptionById(int id) throws SQLException {
        String query = "SELECT id, date_inscription, statut, participant_id, evenement_id " +
                       "FROM inscription WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Inscription(
                        rs.getInt("id"),
                        rs.getDate("date_inscription"),
                        rs.getString("statut"),
                        rs.getInt("participant_id"),
                        rs.getInt("evenement_id")
                    );
                }
            }
        }
        return null; // Aucun résultat trouvé
    }

    @Override
    public List<Inscription> getAllInscriptions() throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT id, date_inscription, statut, participant_id, evenement_id FROM inscription";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                inscriptions.add(new Inscription(
                    rs.getInt("id"),
                    rs.getDate("date_inscription"),
                    rs.getString("statut"),
                    rs.getInt("participant_id"),
                    rs.getInt("evenement_id")
                ));
            }
        }
        return inscriptions;
    }

    @Override
    public void updateInscription(Inscription inscription) throws SQLException {
        String query = "UPDATE inscription SET date_inscription = ?, statut = ?, " +
                       "participant_id = ?, evenement_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(inscription.getDateInscription().getTime()));
            pstmt.setString(2, inscription.getStatut());
            pstmt.setInt(3, inscription.getParticipantId());
            pstmt.setInt(4, inscription.getEvenementId());
            pstmt.setInt(5, inscription.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteInscription(int id) throws SQLException {
        String query = "DELETE FROM inscription WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Inscription> getInscriptionsByParticipant(int participantId) throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT id, date_inscription, statut, evenement_id " +
                       "FROM inscription WHERE participant_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, participantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    inscriptions.add(new Inscription(
                        rs.getInt("id"),
                        rs.getDate("date_inscription"),
                        rs.getString("statut"),
                        participantId,
                        rs.getInt("evenement_id")
                    ));
                }
            }
        }
        return inscriptions;
    }
    
    @Override
    public List<Inscription> getInscriptionsByEvent(int eventId) throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT id, date_inscription, statut, participant_id, evenement_id " +
                       "FROM inscription WHERE evenement_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    inscriptions.add(new Inscription(
                        rs.getInt("id"),
                        rs.getDate("date_inscription"),
                        rs.getString("statut"),
                        rs.getInt("participant_id"),
                        rs.getInt("evenement_id")
                    ));
                }
            }
        }
        return inscriptions;
    }

}
