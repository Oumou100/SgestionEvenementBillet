package rmi;

import metier.Utilisateur;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUserServiceRemote extends Remote {
    // Authentifier un utilisateur
    String authenticate(String email, String password) throws RemoteException;

    // Ajouter un nouvel utilisateur
    boolean createUser(String nom, String email, String password, String role) throws RemoteException;
    
    // Modifier les informations d’un utilisateur
    boolean updateUser(int userId, String newName, String newEmail, String newPassword, String newRole) throws RemoteException;

    // Récupérer les informations d’un utilisateur par son ID
    Utilisateur getUserDetails(int userId) throws RemoteException;
    
    // Récupérer tous les utilisateurs
    List<Utilisateur> getAllUsers() throws RemoteException;

    // Supprimer un utilisateur
    boolean deleteUser(int userId) throws RemoteException;
    
    public boolean logoutUser(int userId) throws RemoteException;
}
