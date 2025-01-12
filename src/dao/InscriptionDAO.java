package dao;

import metier.Inscription;

import java.sql.SQLException;
import java.util.List;

public interface InscriptionDAO {
    void createInscription(Inscription inscription) throws SQLException;   // Créer une inscription
    Inscription getInscriptionById(int id) throws SQLException;           // Lire une inscription par ID
    List<Inscription> getAllInscriptions() throws SQLException;           // Lire toutes les inscriptions
    void updateInscription(Inscription inscription) throws SQLException;   // Mettre à jour une inscription
    void deleteInscription(int id) throws SQLException;                    // Supprimer une inscription
    List<Inscription> getInscriptionsByParticipant(int participantId) throws SQLException; // Lire les inscriptions d'un participant
    List<Inscription> getInscriptionsByEvent(int eventId) throws SQLException;
}
