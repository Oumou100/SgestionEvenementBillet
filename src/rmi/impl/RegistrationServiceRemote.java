package rmi.impl;

import dao.InscriptionDAO;
import dao.UtilisateurDAO;
import dao.EvenementDAO;
import metier.Inscription;
import metier.Utilisateur;
import metier.Evenement;
import rmi.IRegistrationServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationServiceRemote extends UnicastRemoteObject implements IRegistrationServiceRemote {

    private final InscriptionDAO inscriptionDAO;
    private final UtilisateurDAO utilisateurDAO;
    private final EvenementDAO evenementDAO;

    // Constructeur
    public RegistrationServiceRemote(Connection connection) throws RemoteException {
        super(); // Initialisation de la classe UnicastRemoteObject
        this.inscriptionDAO = new dao.impl.InscriptionDAOImpl(connection);
        this.utilisateurDAO = new dao.impl.UtilisateurDAOImpl(connection);
        this.evenementDAO = new dao.impl.EvenementDAOImpl(connection);
    }

    @Override
    public boolean registerParticipant(int eventId, int participantId) throws RemoteException {
        try {
            // Vérifier si l'utilisateur est un participant
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(participantId);
            if (utilisateur == null || !"participant".equalsIgnoreCase(utilisateur.getRole())) {
                System.out.println("L'utilisateur n'est pas un participant.");
                return false;
            }

            // Vérifier la disponibilité des places pour l'événement
            if (!checkAvailability(eventId)) {
                System.out.println("Pas de places disponibles pour cet événement.");
                return false;
            }

            // Créer une nouvelle inscription
            Inscription inscription = new Inscription(
                0,
                new Date(), // Date actuelle
                "confirmee", // Statut initial
                participantId,
                eventId
            );

            // Enregistrer l'inscription dans la base de données
            inscriptionDAO.createInscription(inscription);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkAvailability(int eventId) throws RemoteException {
        try {
            // Récupérer l'événement par son ID
            Evenement evenement = evenementDAO.getEvenementById(eventId);
            if (evenement == null) {
                System.out.println("Événement introuvable.");
                return false;
            }

            // Récupérer le nombre d'inscriptions actuelles pour cet événement
            List<Inscription> inscriptions = inscriptionDAO.getInscriptionsByEvent(eventId);
            int currentRegistrations = inscriptions.size();

            // Comparer avec la capacité maximale de l'événement
            return currentRegistrations < evenement.getCapacite();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Utilisateur> getParticipants(int eventId) throws RemoteException {
        try {
            // Récupérer toutes les inscriptions pour l'événement donné
            List<Inscription> inscriptions = inscriptionDAO.getInscriptionsByEvent(eventId);
            List<Utilisateur> participants = new ArrayList<>();

            for (Inscription inscription : inscriptions) {
                // Récupérer les détails de chaque participant
                Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(inscription.getParticipantId());
                if (utilisateur != null && "participant".equalsIgnoreCase(utilisateur.getRole())) {
                    participants.add(utilisateur);
                }
            }
            return participants;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean cancelRegistration(int registrationId) throws RemoteException {
        try {
            // Supprimer l'inscription
            inscriptionDAO.deleteInscription(registrationId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRegistration(int registrationId, int newEventId, int participantId) throws RemoteException {
        try {
            // Récupérer l'inscription existante
            Inscription inscription = inscriptionDAO.getInscriptionById(registrationId);
            if (inscription == null) {
                System.out.println("Inscription non trouvée.");
                return false;
            }

            // Vérifier si l'utilisateur est un participant
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(participantId);
            if (utilisateur == null || !"participant".equalsIgnoreCase(utilisateur.getRole())) {
                System.out.println("L'utilisateur n'est pas un participant.");
                return false;
            }

            // Vérifier la disponibilité des places pour le nouvel événement
            if (!checkAvailability(newEventId)) {
                System.out.println("Pas de places disponibles pour ce nouvel événement.");
                return false;
            }

            // Mettre à jour les informations de l'inscription
            inscription.setEvenementId(newEventId);
            inscriptionDAO.updateInscription(inscription);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Inscription getRegistrationDetails(int registrationId) throws RemoteException {
        try {
            // Récupérer les détails de l'inscription
            return inscriptionDAO.getInscriptionById(registrationId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
