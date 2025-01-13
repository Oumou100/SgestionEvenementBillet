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
        
        this.billetDAO = billetDAO;
    }

    @Override
    public boolean generateTicket(int participantId, int eventId) throws RemoteException {
        try {
            // G�n�ration d'un code QR unique pour le billet
            String ticketCode = generateUniqueCode();
            // On r�cup�re l'�v�nement et le prix associ�
            float eventPrice = getEventPrice(eventId);

            // Cr�er le billet
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
            // R�cup�rer le billet existant par son code QR
            Billet billet = getBilletByCode(ticketCode);
            if (billet == null) {
                return false; // Le billet n'existe pas
            }

            // Mettre � jour les informations du billet
            billet.setEvenementId(newEventId);
            billet.setParticipantId(newParticipantId);
            billet.setParticipantId(newParticipantId);
            billet.setPrix(getEventPrice(newEventId));

            // Sauvegarder les modifications dans la base de donn�es
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
            // Exception sp�cifique li�e � la base de donn�es
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
            // R�cup�rer le billet par son code QR
            Billet billet = getBilletByCode(ticketCode);
            if (billet == null || billet.getStatut().equals("valide")) {
                return false; // Le billet est d�j� valide ou n'existe pas
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
            // R�cup�rer le billet par son code QR
            Billet billet = getBilletByCode(ticketCode);
            if (billet == null || billet.getStatut().equals("annule")) {
                return false; // Le billet est d�j� annul� ou n'existe pas
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

    // M�thode pour g�n�rer un code QR unique (par exemple, bas� sur l'ID du billet)
    private String generateUniqueCode() {
        // Impl�mentez une m�thode pour g�n�rer un code QR unique, par exemple avec un UUID ou une concat�nation d'ID
        return "QR-" + System.currentTimeMillis();
    }

    // M�thode pour obtenir le prix de l'�v�nement (supposons qu'il existe une m�thode dans la base de donn�es)
    private float getEventPrice(int eventId) {
        // Impl�mentez la r�cup�ration du prix de l'�v�nement � partir de la base de donn�es
        // Exemple : 
        // return eventDAO.getEventById(eventId).getPrix();
        return 20.0f; // Exemple fixe
    }

    // M�thode pour obtenir le titre de l'�v�nement (similaire � getEventPrice)
    private String getEventTitle(int eventId) {
        // Impl�mentez la r�cup�ration du titre de l'�v�nement
        // Exemple : 
        // return eventDAO.getEventById(eventId).getTitre();
        return "Exemple d'�v�nement"; // Exemple fixe
    }

    // M�thode pour obtenir le nom du participant
    private String getParticipantName(int participantId) {
        // Impl�mentez la r�cup�ration du nom du participant � partir de la base de donn�es
        // Exemple : 
        // return userDAO.getUserById(participantId).getNom();
        return "Participant " + participantId; // Exemple fixe
    }

    // M�thode pour obtenir un billet par son code QR
    private Billet getBilletByCode(String ticketCode) {
        try {
            // Impl�mentation pour r�cup�rer le billet � partir du code QR
            for (Billet billet : billetDAO.getAllBillets()) {
                if (billet.getCode().equals(ticketCode)) {
                    return billet;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Billet non trouv�
    }

	@Override
	public List<String> getTicketsbyEvenement(int id_evenement) throws RemoteException {
		// TODO Auto-generated method stub
		List<String> list = billetDAO.getTicketsbyEvenement(id_evenement);
		return list;
	}

	@Override
	public boolean deleteTikectbyId(int id_billet) throws Exception {
		// TODO Auto-generated method stub
		boolean t=false;
		t=billetDAO.deleteTikectbyId(id_billet);
		return t;
	}
	
}
