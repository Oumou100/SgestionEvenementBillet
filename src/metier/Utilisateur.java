package metier;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private String role;
    private boolean isAuthentified; // Nouveau champ


    // Constructeur
    public Utilisateur(int id, String nom, String email, String motDePasse, String role,boolean isAuthentified) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.isAuthentified = isAuthentified;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAuthentified() {
        return isAuthentified;
    }

    public void setAuthentified(boolean authentified) {
        isAuthentified = authentified;
    }
    
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isAuthentified=" + isAuthentified +
                '}';
    }
}
