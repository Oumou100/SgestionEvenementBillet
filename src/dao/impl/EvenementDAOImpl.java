package dao.impl;

import dao.EvenementDAO;
import metier.Billet;
import metier.Evenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAOImpl implements EvenementDAO {
    private final Connection connection;

    // Constructeur avec connexion
    public EvenementDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createEvenement(Evenement evenement) throws Exception {
        // Étape 1: Créer l'événement dans la base de données
        String query = "INSERT INTO evenement (titre, description, date, lieu, capacite, prix, utilisateur_id) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, evenement.getTitre());
            pstmt.setString(2, evenement.getDescription());
            pstmt.setDate(3, new java.sql.Date(evenement.getDate().getTime()));
            pstmt.setString(4, evenement.getLieu());
            pstmt.setInt(5, evenement.getCapacite());
            pstmt.setFloat(6, evenement.getPrix());
            pstmt.setInt(7, evenement.getOrganisateurId());
            pstmt.executeUpdate();
            // Récupérer l'ID de l'événement généré pour pouvoir l'utiliser dans la génération des billets
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int evenementId = generatedKeys.getInt(1);  // L'ID de l'événement créé
                    // Étape 2: Générer les billets pour l'événement
                    generateTickets(evenementId, evenement.getCapacite(), evenement.getPrix());
                    
                    // Étape 3: Mettre à jour l'événement pour marquer que les billets ont été générés
                    String updateQuery = "UPDATE evenement SET tickets_are_generated = 1 WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, evenementId);
                        updateStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public void generateTickets(int evenementId, int capacite, float prix) throws Exception {
        // Générer les billets en fonction de la capacité de l'événement
        String query = "INSERT INTO billet (code_qr, date_emission, prix, statut, participant_id, evenement_id) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < capacite; i++) {
                String codeQR = generateUniqueQRCode(); // Générer un QR code unique pour chaque billet
                pstmt.setString(1, codeQR);
                pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));  // Date d'émission actuelle
                pstmt.setFloat(3, prix);   // Prix du billet (celui de l'événement)
                pstmt.setString(4, "disponible"); // Statut du billet (disponible)
                pstmt.setNull(5, java.sql.Types.INTEGER); // Pas de participant assigné au moment de la création
 // Aucun participant pour l'instant (peut être mis à jour plus tard)
                pstmt.setInt(6, evenementId);  // L'ID de l'événement
                pstmt.addBatch();  // Ajouter au batch pour insertion en une seule fois
            }
            pstmt.executeBatch();  // Exécution de l'insertion de tous les billets
        }
    }

    public void updateTicketPrices(int eventId, float newPrice) throws Exception {
        String query = "UPDATE billet SET prix = ? WHERE evenement_id = ? AND statut = 'disponible'";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setFloat(1, newPrice);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
        }
    }
    @Override
    public Evenement getEvenementById(int id) throws Exception {
        String query = "SELECT * FROM evenement WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Evenement(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("lieu"),
                        rs.getInt("capacite"),
                        rs.getFloat("prix"),
                        rs.getInt("utilisateur_id")
                    );
                }
            }
        }
        return null; // Aucun événement trouvé
    }

    @Override
    public List<Evenement> getAllEvenements() throws Exception {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                evenements.add(new Evenement(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getDate("date"),
                    rs.getString("lieu"),
                    rs.getInt("capacite"),
                    rs.getFloat("prix"),
                    rs.getInt("utilisateur_id")
                ));
            }
        }
        return evenements;
    }

    @Override
    public void updateEvenement(Evenement evenement) throws Exception {
        // 1. Récupérer l'ancienne capacité et le nombre de billets vendus
        int oldCapacity = 0;
        int soldTickets = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(
                "SELECT e.capacite, COUNT(b.id) as billets_vendus " +
                "FROM evenement e " +
                "LEFT JOIN billet b ON e.id = b.evenement_id AND b.statut != 'disponible' " +
                "WHERE e.id = ? " +
                "GROUP BY e.capacite")) {
            pstmt.setInt(1, evenement.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                oldCapacity = rs.getInt("capacite");
                soldTickets = rs.getInt("billets_vendus");
            }
        }

        // 2. Vérifier si la nouvelle capacité est suffisante pour les billets déjà vendus
        if (evenement.getCapacite() < soldTickets) {
        	System.out.println("La nouvelle capacité (" + evenement.getCapacite() + 
                              ") est inférieure au nombre de billets déjà vendus (" + soldTickets + ")");
        	return;
            
        }

        // 3. Supprimer tous les billets disponibles
        String deleteQuery = "DELETE FROM billet WHERE evenement_id = ? AND statut = 'disponible'";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, evenement.getId());
            pstmt.executeUpdate();
        }

        // 4. Mettre à jour l'événement
        String updateQuery = "UPDATE evenement SET titre = ?, description = ?, date = ?, lieu = ?, capacite = ?, prix = ? " +
                            "WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, evenement.getTitre());
            pstmt.setString(2, evenement.getDescription());
            pstmt.setDate(3, new java.sql.Date(evenement.getDate().getTime()));
            pstmt.setString(4, evenement.getLieu());
            pstmt.setInt(5, evenement.getCapacite());
            pstmt.setFloat(6, evenement.getPrix());
            pstmt.setInt(7, evenement.getId());
            pstmt.executeUpdate();
            updateTicketPrices(evenement.getId(), evenement.getPrix());
        }

        // 5. Générer les nouveaux billets disponibles
        int newTicketsNeeded = evenement.getCapacite() - soldTickets;
        if (newTicketsNeeded > 0) {
            generateTickets(evenement.getId(), newTicketsNeeded, evenement.getPrix());
        }
    }


    @Override
    public void deleteEvenement(int id) throws Exception {
        // 1. Supprimer les billets associés à l'événement
        String deleteTicketsQuery = "DELETE FROM billet WHERE evenement_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteTicketsQuery)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Tous les billets associés à l'événement ont été supprimés.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des billets : " + e.getMessage());
            throw new Exception("Erreur lors de la suppression des billets.");
        }

        // 2. Supprimer l'événement
        String deleteEventQuery = "DELETE FROM evenement WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteEventQuery)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Événement supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
            throw new Exception("Erreur lors de la suppression de l'événement.");
        }
    }

    
    @Override
    public List<Evenement> getEvenementsByOrganisateurId(int organisateurId) throws Exception {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement WHERE utilisateur_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, organisateurId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    evenements.add(new Evenement(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("lieu"),
                        rs.getInt("capacite"),
                        rs.getFloat("prix"),
                        rs.getInt("utilisateur_id")
                    ));
                }
            }
        }
        return evenements;
    }
    
    public void generateTickets(int evenementId) throws Exception {
        // Récupérer les informations de l'événement pour connaître la capacité
        /*String getEventQuery = "SELECT capacite, organisateur_id FROM evenement WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(getEventQuery)) {
            pstmt.setInt(1, evenementId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int nbBillet = rs.getInt("capacite");
                    int organisateurId = rs.getInt("organisateur_id");

                    // Générer les billets
                    for (int i = 0; i < nbBillet; i++) {
                        String codeQR = generateQRCode();  // Vous pouvez utiliser une bibliothèque pour générer un QR Code
                        createBillet(new Billet(codeQR, new java.sql.Date(System.currentTimeMillis()), 
                                                rs.getFloat("prix"), "disponible", organisateurId, evenementId));
                    }

                    // Mettre à jour la table evenement pour marquer que les billets ont été générés
                    String updateQuery = "UPDATE evenement SET ticket_are_generated = 1 WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, evenementId);
                        updateStmt.executeUpdate();
                    }
                }
            }
        }*/
    }

    private String generateUniqueQRCode() throws SQLException {
        String codeQR="";
        boolean isUnique = false;

        // Requête pour vérifier l'unicité dans la base de données
        String query = "SELECT COUNT(*) FROM billet WHERE code_qr = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            while (!isUnique) {
                // Générer un code QR
                long timestamp = System.currentTimeMillis();
                int random = (int) (Math.random() * 100000); // Nombre aléatoire entre 0 et 99,999
                codeQR = "QR_" + timestamp + "_" + random;

                // Vérifier dans la base de données
                pstmt.setString(1, codeQR);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        isUnique = true; // Si aucun résultat trouvé, le code est unique
                    }
                }
            }
        }
        return codeQR; // Retourner un code QR unique
    }

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

}
