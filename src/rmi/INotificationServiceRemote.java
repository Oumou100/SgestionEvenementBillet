package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotificationServiceRemote extends Remote {
    // Envoyer une notification au client
    void sendNotification(String message) throws RemoteException;

    // Recevoir des mises � jour en temps r�el (callback)
    void registerForUpdates(int clientId) throws RemoteException;

    // Supprimer un client de la liste des abonn�s
    void unregisterFromUpdates(int clientId) throws RemoteException;
}
