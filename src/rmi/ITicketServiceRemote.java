package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import metier.Billet;

public interface ITicketServiceRemote extends Remote {
    // G�n�rer un billet pour un participant
    boolean generateTicket(int participantId, int eventId) throws RemoteException;

    // Modifier un billet existant
    boolean updateTicket(String ticketCode, int newEventId, int newParticipantId) throws RemoteException;

    // Obtenir les billets d�un participant
    List<Billet> getTickets(int participantId) throws RemoteException;

    // Valider un billet (organisateur ou syst�me)
    boolean validateTicket(String ticketCode) throws RemoteException;

    // Annuler un billet
    boolean cancelTicket(String ticketCode) throws RemoteException;
    
    List<String> getTicketsbyEvenement(int id_evenement)throws RemoteException;
    
    public boolean deleteTikectbyId(int id_billet) throws Exception;
}
