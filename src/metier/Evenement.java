package metier;

import java.io.Serializable;
import java.util.Date;

public class Evenement implements Serializable {
	private static final long serialVersionUID = 1L;
    private int id;
    private String titre;
    private String description;
    private Date date;
    private String lieu;
    private int capacite;
    private float prix;
    private boolean tikets_are_generated;
    public boolean isTikets_are_generated() {
		return tikets_are_generated;
	}

	public void setTikets_are_generated(boolean tikets_are_generated) {
		this.tikets_are_generated = tikets_are_generated;
	}

	private int organisateurId;

    public Evenement(int id, String titre, String description, Date date, String lieu, int capacite, float prix, int organisateurId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.capacite = capacite;
        this.prix = prix;
        this.organisateurId = organisateurId;
    }

    // Getters et Setters...

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public int getOrganisateurId() {
		return organisateurId;
	}

	public void setOrganisateurId(int organisateurId) {
		this.organisateurId = organisateurId;
	}

	@Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", capacite=" + capacite +
                ", prix=" + prix +
                '}';
    }
}
