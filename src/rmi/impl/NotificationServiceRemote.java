package rmi.impl;

import rmi.INotificationServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class NotificationServiceRemote extends UnicastRemoteObject implements INotificationServiceRemote {
    // Map pour stocker les abonn�s (par clientId)
    private final Map<Integer, String> registeredClients;

    // Constructeur
    public NotificationServiceRemote() throws RemoteException {
        super();
        this.registeredClients = new HashMap<>();
    }

    @Override
    public void sendNotification(String message) throws RemoteException {
        // Envoyer une notification � tous les clients enregistr�s
        System.out.println("Envoi des notifications aux clients enregistr�s...");
        for (Map.Entry<Integer, String> entry : registeredClients.entrySet()) {
            System.out.println("Notification envoy�e au client " + entry.getKey() + ": " + message);
            // En production, il serait possible d'utiliser un canal de communication r�el ici.
        }
    }

    @Override
    public void registerForUpdates(int clientId) throws RemoteException {
        if (!registeredClients.containsKey(clientId)) {
            registeredClients.put(clientId, "Client" + clientId); // Ajout d'un identifiant fictif pour simplification
            System.out.println("Client " + clientId + " enregistr� pour recevoir les mises � jour.");
        } else {
            System.out.println("Client " + clientId + " est d�j� enregistr�.");
        }
    }

    @Override
    public void unregisterFromUpdates(int clientId) throws RemoteException {
        if (registeredClients.containsKey(clientId)) {
            registeredClients.remove(clientId);
            System.out.println("Client " + clientId + " d�senregistr� des mises � jour.");
        } else {
            System.out.println("Client " + clientId + " n'est pas enregistr�.");
        }
    }
}
