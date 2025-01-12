package dao.impl;

import dao.UtilisateurDAO;
import metier.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private final Connection connection;

    // Constructeur pour injecter une connexion à la base de données
    public UtilisateurDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) throws Exception {
        String query = "INSERT INTO utilisateur (nom, email, mot_de_passe, role, is_authentified) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setBoolean(5, utilisateur.isAuthentified()); // Nouveau champ
            pstmt.executeUpdate();
        }
    }

    @Override
    public Utilisateur getUtilisateurById(int id) throws Exception {
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role"),
                        rs.getBoolean("is_authentified") // Nouveau champ
                    );
                }
            }
        }
        return null; // Si aucun utilisateur trouvé
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() throws Exception {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("role"),
                    rs.getBoolean("is_authentified") // Nouveau champ
                ));
            }
        }
        return utilisateurs;
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) throws Exception {
        String query = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ?, role = ?, is_authentified = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setBoolean(5, utilisateur.isAuthentified()); // Nouveau champ
            pstmt.setInt(6, utilisateur.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteUtilisateur(int id) throws Exception {
        String query = "DELETE FROM utilisateur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
