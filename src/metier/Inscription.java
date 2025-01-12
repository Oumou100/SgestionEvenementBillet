package metier;

import java.io.Serializable;
import java.util.Date;

public class Inscription implements Serializable {
    private int id;
    private Date dateInscription;
    private String statut;
    private int participantId;
    private int evenementId;

    // Constructeur
    public Inscription(int id, Date dateInscription, String statut, int participantId, int evenementId) {
        this.id = id;
        this.dateInscription = dateInscription;
        this.statut = statut;
        this.participantId = participantId;
        this.evenementId = evenementId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
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

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", dateInscription=" + dateInscription +
                ", statut='" + statut + '\'' +
                ", participantId=" + participantId +
                ", evenementId=" + evenementId +
                '}';
    }
}
