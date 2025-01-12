package rmi;

import metier.Evenement; // Assurez-vous d'importer la classe Evenement
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IEventServiceRemote extends Remote {
    // Créer un nouvel événement
    boolean createEvent(String titre, String description, String date, String lieu, int capacite, double prix, int organisateurId) throws RemoteException;
    
    // Modifier les détails d’un événement
    boolean updateEvent(int eventId, String titre, String description, String date, String lieu, int capacite, double prix) throws RemoteException;
    
    // Récupérer la liste des événements disponibles
    List<Evenement> getAllEvents() throws RemoteException;  // Retourne une liste d'objets Evenement

    // Obtenir les détails d’un événement spécifique
    Evenement getEventDetails(int eventId) throws RemoteException;  // Retourne un objet Evenement

    // Supprimer un événement (organisateur uniquement)
    boolean deleteEvent(int eventId) throws RemoteException;
    
    public List<Evenement> getEventsByOrganisateurId(int organisateurId) throws RemoteException;
    public void generateTicketsForEvent(int evenementId) throws RemoteException;


}
