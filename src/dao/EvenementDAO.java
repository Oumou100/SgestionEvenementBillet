package dao;

import metier.Evenement;
import java.util.List;

public interface EvenementDAO {
    // Créer un événement
    void createEvenement(Evenement evenement) throws Exception;

    // Lire un événement par ID
    Evenement getEvenementById(int id) throws Exception;

    // Lire tous les événements
    List<Evenement> getAllEvenements() throws Exception;

    List<Evenement> getUpcomingEvenements() throws Exception;

    // Mettre à jour un événement
    void updateEvenement(Evenement evenement) throws Exception;

    // Supprimer un événement par ID
    void deleteEvenement(int id) throws Exception;
     List<Evenement> getEvenementsByOrganisateurId(int organisateurId) throws Exception;
    void generateTickets(int evenementId) throws Exception;
}
