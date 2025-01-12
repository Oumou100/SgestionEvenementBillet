package rmi.impl;

import rmi.INotificationServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class NotificationServiceRemote extends UnicastRemoteObject implements INotificationServiceRemote {
    // Map pour stocker les abonnés (par clientId)
    private final Map<Integer, String> registeredClients;

    // Constructeur
    public NotificationServiceRemote() throws RemoteException {
        super();
        this.registeredClients = new HashMap<>();
    }

    @Override
    public void sendNotification(String message) throws RemoteException {
        // Envoyer une notification à tous les clients enregistrés
        System.out.println("Envoi des notifications aux clients enregistrés...");
        for (Map.Entry<Integer, String> entry : registeredClients.entrySet()) {
            System.out.println("Notification envoyée au client " + entry.getKey() + ": " + message);
            // En production, il serait possible d'utiliser un canal de communication réel ici.
        }
    }

    @Override
    public void registerForUpdates(int clientId) throws RemoteException {
        if (!registeredClients.containsKey(clientId)) {
            registeredClients.put(clientId, "Client" + clientId); // Ajout d'un identifiant fictif pour simplification
            System.out.println("Client " + clientId + " enregistré pour recevoir les mises à jour.");
        } else {
            System.out.println("Client " + clientId + " est déjà enregistré.");
        }
    }

    @Override
    public void unregisterFromUpdates(int clientId) throws RemoteException {
        if (registeredClients.containsKey(clientId)) {
            registeredClients.remove(clientId);
            System.out.println("Client " + clientId + " désenregistré des mises à jour.");
        } else {
            System.out.println("Client " + clientId + " n'est pas enregistré.");
        }
    }
}
