/*package dao;

import metier.Utilisateur;
import java.util.List;

public interface UtilisateurDAO {
    // Créer un utilisateur
    void createUtilisateur(Utilisateur utilisateur) throws Exception;

    // Lire un utilisateur par ID
    Utilisateur getUtilisateurById(int id) throws Exception;

    // Lire tous les utilisateurs
    List<Utilisateur> getAllUtilisateurs() throws Exception;

    // Mettre à jour un utilisateur
    void updateUtilisateur(Utilisateur utilisateur) throws Exception;

    // Supprimer un utilisateur par ID
    void deleteUtilisateur(int id) throws Exception;
}
*/
package dao;

import metier.Utilisateur;

import java.rmi.RemoteException;
import java.util.List;

public interface UtilisateurDAO {
    // Crï¿½er un utilisateur
    void createUtilisateur(Utilisateur utilisateur) throws Exception;

    // Lire un utilisateur par ID
    Utilisateur getUtilisateurById(int id) throws Exception;

    // Lire tous les utilisateurs
    List<Utilisateur> getAllUtilisateurs() throws Exception;

    // Mettre ï¿½ jour un utilisateur
    void updateUtilisateur(Utilisateur utilisateur) throws Exception;
    
    boolean updateNameUser(int id_user,String newName) throws Exception;
    boolean updateEmailUser(int id_user,String newEmail) throws Exception;
    boolean updatePasswordUser(int id_user,String newPwd) throws Exception;
    boolean updateRoleUser(int id_user,String newRole) throws Exception;

    // Supprimer un utilisateur par ID
    void deleteUtilisateur(int id) throws Exception;
    //acheter un billet
    boolean acheterBillet(int id_user, int id_billet)throws RemoteException;
  //lister mes achats
    List<String> mesAchats(int id_user)throws Exception;
    //lister mes evenements
    List<String> mesEvenement(int id_user)throws Exception;
    
}
