package rmi.impl;

import dao.UtilisateurDAO;
import metier.Utilisateur;
import rmi.IUserServiceRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;

public class UserServiceRemote extends UnicastRemoteObject implements IUserServiceRemote {

    private final UtilisateurDAO utilisateurDAO;

    // Constructeur avec connexion � la base de donn�es
    public UserServiceRemote(Connection connection) throws RemoteException {
        super(); // N�cessaire pour les classes RMI
        this.utilisateurDAO = new dao.impl.UtilisateurDAOImpl(connection);
    }

    @Override
    public String authenticate(String email, String password) throws RemoteException {
        try {
            // R�cup�rer tous les utilisateurs
            List<Utilisateur> utilisateurs = utilisateurDAO.getAllUtilisateurs();

            // Hacher le mot de passe de l'utilisateur fourni
            String hashedPassword = hashPassword(password);

            // Rechercher un utilisateur correspondant aux informations d'authentification
            for (Utilisateur utilisateur : utilisateurs) {
                // V�rifier les informations d'authentification
            	if(utilisateur.getEmail().equals(email)) {
            		if(utilisateur.isAuthentified()) {
            			System.out.println("Cet utilisateurest deja authentifie");
            			return "false";
            		}
            	}
                if (utilisateur.getEmail().equals(email) && utilisateur.getMotDePasse().equals(hashedPassword)) {
                    // Authentification r�ussie, mettre � jour l'�tat is_authentified dans la base
                    utilisateur.setAuthentified(true);
                    utilisateurDAO.updateUtilisateur(utilisateur); // Sauvegarder cet �tat
                    return utilisateur.getId() + "," + utilisateur.getRole();  // Authentification r�ussie
                }
            }
            return "false"; // Authentification �chou�e
        } catch (Exception e) {
            e.printStackTrace();
            return "false"; // Erreur d'authentification
        }
    }



    @Override
    public boolean createUser(String nom, String email, String password, String role) throws RemoteException {
        try {
            // Hacher le mot de passe avant de le sauvegarder
            String hashedPassword = hashPassword(password);

            // Cr�er un objet utilisateur avec is_authentified � false
            Utilisateur utilisateur = new Utilisateur(0, nom, email, hashedPassword, role, false);

            // Ajouter l'utilisateur via DAO
            utilisateurDAO.createUtilisateur(utilisateur);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(int userId, String newName, String newEmail, String newPassword, String newRole) throws RemoteException {
        try {
            // R�cup�rer l'utilisateur existant
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(userId);
            String hashedPassword = hashPassword(newPassword);
            if (utilisateur == null) {
                return false; // Utilisateur introuvable
            }

            // Mettre � jour les informations si elles ne sont pas vides
            if (newName != null && !newName.isEmpty()) {
                utilisateur.setNom(newName);
            }
            if (newEmail != null && !newEmail.isEmpty()) {
                utilisateur.setEmail(newEmail);
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                utilisateur.setMotDePasse(hashedPassword);
            }
            if (newRole != null && !newRole.isEmpty() && (newRole.equals("organisateur") || newRole.equals("participant"))) {
                utilisateur.setRole(newRole);
            }

            // Mise � jour via DAO
            utilisateurDAO.updateUtilisateur(utilisateur);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Utilisateur getUserDetails(int userId) throws RemoteException {
        try {
            // R�cup�rer l'utilisateur via DAO
            return utilisateurDAO.getUtilisateurById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Utilisateur> getAllUsers() throws RemoteException {
        try {
            // Retourner tous les utilisateurs via DAO
            return utilisateurDAO.getAllUtilisateurs();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteUser(int userId) throws RemoteException {
        try {
            // Supprimer l'utilisateur via DAO
            utilisateurDAO.deleteUtilisateur(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean logoutUser(int userId) throws RemoteException {
        try {
            // R�cup�rer l'utilisateur existant
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(userId);
            if (utilisateur == null) {
                return false; // Utilisateur introuvable
            }

            // V�rifier si l'utilisateur est d�j� d�connect�
            if (!utilisateur.isAuthentified()) {
                System.out.println("Cet utilisateur n'est pas authentifi�.");
                return false; // L'utilisateur n'est pas authentifi�, pas besoin de le d�connecter
            }

            // Mettre � jour l'�tat is_authentified
            utilisateur.setAuthentified(false);
            utilisateurDAO.updateUtilisateur(utilisateur); // Sauvegarder l'�tat dans la base
            System.out.println("Utilisateur d�connect� avec succ�s.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String hashPassword(String password) {
        try {
            // Cr�er un objet MessageDigest pour SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Appliquer le hachage
            byte[] hashBytes = digest.digest(password.getBytes());
            // Convertir les octets en cha�ne hexad�cimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }

}
