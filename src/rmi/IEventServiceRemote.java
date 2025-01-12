package rmi;

import metier.Evenement; // Assurez-vous d'importer la classe Evenement
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IEventServiceRemote extends Remote {
    // Cr�er un nouvel �v�nement
    boolean createEvent(String titre, String description, String date, String lieu, int capacite, double prix, int organisateurId) throws RemoteException;
    
    // Modifier les d�tails d�un �v�nement
    boolean updateEvent(int eventId, String titre, String description, String date, String lieu, int capacite, double prix) throws RemoteException;
    
    // R�cup�rer la liste des �v�nements disponibles
    List<Evenement> getAllEvents() throws RemoteException;  // Retourne une liste d'objets Evenement

    // Obtenir les d�tails d�un �v�nement sp�cifique
    Evenement getEventDetails(int eventId) throws RemoteException;  // Retourne un objet Evenement

    // Supprimer un �v�nement (organisateur uniquement)
    boolean deleteEvent(int eventId) throws RemoteException;
    
    public List<Evenement> getEventsByOrganisateurId(int organisateurId) throws RemoteException;
    public void generateTicketsForEvent(int evenementId) throws RemoteException;


}
