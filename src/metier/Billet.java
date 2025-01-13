package metier;

import java.io.Serializable;
import java.sql.Date;

public class Billet {
    private int id;
    private String code;
    private Date dateEmission;
    private float prix;
    private String statut;
    private int participantId;
    private int evenementId;
    private boolean ticketAreGenerated;

    // Constructeur sans ID (pour l'insertion)
    public Billet(String code, Date dateEmission, float prix, String statut, int participantId, int evenementId) {
        this.code = code;
        this.dateEmission = dateEmission;
        this.prix = prix;
        this.statut = statut;
        this.participantId = participantId;
        this.evenementId = evenementId;
        this.ticketAreGenerated = false; // Par défaut, les tickets ne sont pas encore générés
    }


    public Billet(int i, String ticketCode, String eventTitle, String participantName, float eventPrice, String string,
			int participantId2, int eventId) {
		// TODO Auto-generated constructor stub
	}
    
    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public Date getDateEmission() {
		return dateEmission;
	}



	public void setDateEmission(Date dateEmission) {
		this.dateEmission = dateEmission;
	}



	public float getPrix() {
		return prix;
	}



	public void setPrix(float prix) {
		this.prix = prix;
	}



	public String getStatut() {
		return statut;
	}



	public void setStatut(String statut) {
		this.statut = statut;
	}



	public int getParticipantId() {
		return participantId;
	}



	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}



	public int getEvenementId() {
		return evenementId;
	}



	public void setEvenementId(int evenementId) {
		this.evenementId = evenementId;
	}



	public boolean isTicketAreGenerated() {
		return ticketAreGenerated;
	}



	public void setTicketAreGenerated(boolean ticketAreGenerated) {
		this.ticketAreGenerated = ticketAreGenerated;
	}



	// Méthode toString pour affichage facile
    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date_emission='" + dateEmission + '\'' +
                ", prix=" + prix +
                ", statut='" + statut + '\'' +
                ", participantId=" + participantId +
                ", evenementId=" + evenementId +
                '}';
    }
}
