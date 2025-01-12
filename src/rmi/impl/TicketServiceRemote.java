package rmi.impl;

import dao.BilletDAO;
import metier.Billet;
import rmi.ITicketServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketServiceRemote extends UnicastRemoteObject implements ITicketServiceRemote {
    private final BilletDAO billetDAO;

    // Constructeur
    public TicketServiceRemote(BilletDAO billetDAO) throws RemoteException {
        super();
        this.billetDAO = billetDAO;
    }

    @Override
    public boolean generateTicket(int participantId, int eventId) throws RemoteException {
        try {
            // Génération d'un code QR unique pour le billet
            String ticketCode = generateUniqueCode();
            // On récupère l'événement et le prix associé
            float eventPrice = getEventPrice(eventId);

            // Créer le billet
            Billet billet = new Billet(0, ticketCode, getEventTitle(eventId), getParticipantName(participantId), eventPrice, "valide", participantId, eventId);
            billetDAO.createBillet(billet);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTicket(String ticketCode, int newEventId, int newParticipantId) throws RemoteException {
        try {
            // Récupérer le billet existant par son code QR
            Billet billet = getBilletByCode(ticketCode);
            if (billet == null) {
                return false; // Le billet n'existe pas
            }

            // Mettre à jour les informations du billet
            billet.setEvenementId(newEventId);
            billet.setParticipantId(newParticipantId);
            billet.setEventTitle(getEventTitle(newEventId));
            billet.setPrix(getEventPrice(newEventId));

            // Sauvegarder les modifications dans la base de données
            billetDAO.updateBillet(billet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Billet> getTickets(int participantId) throws RemoteException {
        try {
            // Obtenir tous les billets pour un participant
            return billetDAO.getTicketsForParticipant(participantId);
        } catch (SQLException e) {
            // Exception spécifique liée à la base de données
            e.printStackTrace();
            return new ArrayList<>();  // Return an empty list in case of an exception
        } catch (Exception e) {
            // Catch any other exception and print the stack trace
            e.printStackTrace();
            return new ArrayList<>();  // Return an empty list in case of an exception
        }
    }


    @Override
    public boolean validateTicket(String ticketCode) throws RemoteException {
        try {
            // Récupérer le billet par son code QR
            Billet billet = getBilletByCode(ticketCode);
            if (billet == null || billet.getStatut().equals("valide")) {
                return false; // Le billet est déjà valide ou n'existe pas
            }

            // Valider le billet
            billet.setStatut("valide");
            billetDAO.updateBillet(billet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelTicket(String ticketCode) throws RemoteException {
        try {
            // Récupérer le billet par son code QR
            Billet billet = getBilletByCode(ticketCode);
            if (billet == null || billet.getStatut().equals("annule")) {
                return false; // Le billet est déjà annulé ou n'existe pas
            }

            // Annuler le billet
            billet.setStatut("annule");
            billetDAO.updateBillet(billet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour générer un code QR unique (par exemple, basé sur l'ID du billet)
    private String generateUniqueCode() {
        // Implémentez une méthode pour générer un code QR unique, par exemple avec un UUID ou une concaténation d'ID
        return "QR-" + System.currentTimeMillis();
    }

    // Méthode pour obtenir le prix de l'événement (supposons qu'il existe une méthode dans la base de données)
    private float getEventPrice(int eventId) {
        // Implémentez la récupération du prix de l'événement à partir de la base de données
        // Exemple : 
        // return eventDAO.getEventById(eventId).getPrix();
        return 20.0f; // Exemple fixe
    }

    // Méthode pour obtenir le titre de l'événement (similaire à getEventPrice)
    private String getEventTitle(int eventId) {
        // Implémentez la récupération du titre de l'événement
        // Exemple : 
        // return eventDAO.getEventById(eventId).getTitre();
        return "Exemple d'événement"; // Exemple fixe
    }

    // Méthode pour obtenir le nom du participant
    private String getParticipantName(int participantId) {
        // Implémentez la récupération du nom du participant à partir de la base de données
        // Exemple : 
        // return userDAO.getUserById(participantId).getNom();
        return "Participant " + participantId; // Exemple fixe
    }

    // Méthode pour obtenir un billet par son code QR
    private Billet getBilletByCode(String ticketCode) {
        try {
            // Implémentation pour récupérer le billet à partir du code QR
            for (Billet billet : billetDAO.getAllBillets()) {
                if (billet.getCode().equals(ticketCode)) {
                    return billet;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Billet non trouvé
    }
}
