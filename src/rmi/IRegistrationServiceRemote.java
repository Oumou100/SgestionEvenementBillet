package rmi;

import metier.Utilisateur;  // Assurez-vous que la classe Utilisateur est correctement import�e
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import metier.Inscription;
public interface IRegistrationServiceRemote extends Remote {
    // Inscrire un participant � un �v�nement
    boolean registerParticipant(int eventId, int participantId) throws RemoteException;
    
    // Modifier une inscription existante
    boolean updateRegistration(int registrationId, int newEventId, int participantId) throws RemoteException;

    // V�rifier la disponibilit� des places pour un �v�nement
    boolean checkAvailability(int eventId) throws RemoteException;

    // Obtenir la liste des participants inscrits � un �v�nement
    List<Utilisateur> getParticipants(int eventId) throws RemoteException;  // Retourne maintenant une liste d'objets Utilisateur

    // Annuler une inscription
    boolean cancelRegistration(int registrationId) throws RemoteException;

    // R�cup�rer une inscription par son ID
    Inscription getRegistrationDetails(int registrationId) throws RemoteException;
}
