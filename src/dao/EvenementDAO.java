package dao;

import metier.Evenement;
import java.util.List;

public interface EvenementDAO {
    // Cr�er un �v�nement
    void createEvenement(Evenement evenement) throws Exception;

    // Lire un �v�nement par ID
    Evenement getEvenementById(int id) throws Exception;

    // Lire tous les �v�nements
    List<Evenement> getAllEvenements() throws Exception;

    List<Evenement> getUpcomingEvenements() throws Exception;

    // Mettre � jour un �v�nement
    void updateEvenement(Evenement evenement) throws Exception;

    // Supprimer un �v�nement par ID
    void deleteEvenement(int id) throws Exception;
     List<Evenement> getEvenementsByOrganisateurId(int organisateurId) throws Exception;
    void generateTickets(int evenementId) throws Exception;
}
