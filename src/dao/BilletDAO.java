package dao;

import metier.Billet;
import java.util.List;

public interface BilletDAO {
    // Créer un billet
   void createBillet(Billet billet) throws Exception;
   
   
/*
    // Obtenir un billet par ID
    Billet getBilletById(int id) throws Exception;

    // Obtenir tous les billets
    List<Billet> getAllBillets() throws Exception;

    // Mettre à jour un billet
    void updateBillet(Billet billet) throws Exception;

    // Supprimer un billet par ID
    void deleteBillet(int id) throws Exception;

    // Obtenir les billets d'un participant
    List<Billet> getTicketsForParticipant(int participantId) throws Exception;*/
}
