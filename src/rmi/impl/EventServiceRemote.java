package rmi.impl;

import dao.EvenementDAO;
import dao.UtilisateurDAO;
import dao.impl.EvenementDAOImpl;
import metier.Evenement;
import metier.Utilisateur;
import rmi.IEventServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventServiceRemote extends UnicastRemoteObject implements IEventServiceRemote {

    private final EvenementDAO evenementDAO;
    private final UtilisateurDAO utilisateurDAO;

    // Constructeur avec une instance de DAO
    public EventServiceRemote(Connection connection) throws RemoteException {
        super(); // N�cessaire pour la classe RMI
        this.evenementDAO = new EvenementDAOImpl(connection);
        this.utilisateurDAO = new dao.impl.UtilisateurDAOImpl(connection);

    }

    @Override
    public boolean createEvent(String titre, String description, String date, String lieu, int capacite, double prix, int organisateurId) throws RemoteException {
        try {
        	Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(organisateurId);
        	if(utilisateur.isAuthentified()) {
            if (utilisateur == null || !"organisateur".equalsIgnoreCase(utilisateur.getRole())) {
                System.out.println("L'utilisateur n'est pas un organisateur.");
                return false;
            }
            // Convertir la cha�ne de date en objet Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(date);
            // Cr�er un objet Evenement
            Evenement evenement = new Evenement(0, titre, description, parsedDate, lieu, capacite, (float) prix, organisateurId);

            // Utiliser le DAO pour ins�rer l'�v�nement
            evenementDAO.createEvenement(evenement);
            evenement.setTikets_are_generated(true);
            return true;
        	}
        	else {
        		return false;
        	}
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEvent(int eventId, String titre, String description, String date, String lieu, int capacite, double prix) throws RemoteException {
        try {
            // Convertir la cha�ne de date en objet Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(date);

            // Cr�er un objet Evenement avec les nouvelles donn�es
            Evenement evenement = new Evenement(eventId, titre, description, parsedDate, lieu, capacite, (float) prix, 0);

            // Utiliser le DAO pour mettre � jour l'�v�nement
            evenementDAO.updateEvenement(evenement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Evenement> getAllEvents() throws RemoteException {
        try {
            // Utiliser le DAO pour r�cup�rer tous les �v�nements
        	
            return evenementDAO.getAllEvenements();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Evenement getEventDetails(int eventId) throws RemoteException {
        try {
            // Utiliser le DAO pour r�cup�rer les d�tails d'un �v�nement sp�cifique
            return evenementDAO.getEvenementById(eventId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteEvent(int eventId) throws RemoteException {
        try {
            // Utiliser le DAO pour supprimer l'�v�nement
        	
            evenementDAO.deleteEvenement(eventId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Evenement> getEventsByOrganisateurId(int organisateurId) throws RemoteException {
        try {
            // Utiliser le DAO pour r�cup�rer les �v�nements filtr�s par organisateur
            return evenementDAO.getEvenementsByOrganisateurId(organisateurId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void generateTicketsForEvent(int evenementId) throws RemoteException {
        try {
            // Appeler le DAO pour g�n�rer les billets
            evenementDAO.generateTickets(evenementId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
