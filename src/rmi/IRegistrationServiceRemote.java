package rmi;

import metier.Utilisateur;  // Assurez-vous que la classe Utilisateur est correctement importée
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import metier.Inscription;
public interface IRegistrationServiceRemote extends Remote {
    // Inscrire un participant à un événement
    boolean registerParticipant(int eventId, int participantId) throws RemoteException;
    
    // Modifier une inscription existante
    boolean updateRegistration(int registrationId, int newEventId, int participantId) throws RemoteException;

    // Vérifier la disponibilité des places pour un événement
    boolean checkAvailability(int eventId) throws RemoteException;

    // Obtenir la liste des participants inscrits à un événement
    List<Utilisateur> getParticipants(int eventId) throws RemoteException;  // Retourne maintenant une liste d'objets Utilisateur

    // Annuler une inscription
    boolean cancelRegistration(int registrationId) throws RemoteException;

    // Récupérer une inscription par son ID
    Inscription getRegistrationDetails(int registrationId) throws RemoteException;
}
