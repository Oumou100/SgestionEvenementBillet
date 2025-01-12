package dao;

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
