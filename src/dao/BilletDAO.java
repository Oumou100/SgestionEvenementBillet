package dao;

import metier.Billet;

import java.rmi.RemoteException;
import java.util.List;

public interface BilletDAO {
    // Cr�er un billet
   void createBillet(Billet billet) throws Exception;
   
   List<String> getTicketsbyEvenement(int id_evenement)throws RemoteException;

    // Obtenir un billet par ID
    Billet getBilletById(int id) throws Exception;

    // Obtenir tous les billets
    List<Billet> getAllBillets() throws Exception;

    // Mettre � jour un billet
    void updateBillet(Billet billet) throws Exception;

    // Supprimer un billet par ID
    void deleteBillet(int id) throws Exception;

    // Obtenir les billets d'un participant
    List<Billet> getTicketsForParticipant(int participantId) throws Exception;
    
    public boolean deleteTikectbyId(int id_billet) throws Exception;
}
