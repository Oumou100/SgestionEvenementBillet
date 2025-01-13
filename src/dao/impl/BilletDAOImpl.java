package dao.impl;

import dao.BilletDAO;
import metier.Billet;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BilletDAOImpl implements BilletDAO {
    private final Connection connection;
	

    // Constructeur avec connexion
    public BilletDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createBillet(Billet billet) throws Exception {
        String query = "INSERT INTO billet (code_qr, date_emission, prix, statut, participant_id, evenement_id, tickets_are_generated) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, billet.getCode());  // Le code QR du billet
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));  // Date d'�mission actuelle
            pstmt.setFloat(3, billet.getPrix());   // Prix du billet
            pstmt.setString(4, billet.getStatut()); // Statut initial du billet (ex : "disponible")
            
            // Si le billet est g�n�r� sans participant, on laisse le participant_id � 0
            pstmt.setInt(5, billet.getParticipantId()); // Participant ID (0 si non attribu�)
            
            pstmt.setInt(6, billet.getEvenementId());  // ID de l'�v�nement pour lequel le billet est cr��
            pstmt.setBoolean(7, billet.isTicketAreGenerated()); // D�finir si les billets sont g�n�r�s (true/false)
            
            pstmt.executeUpdate();  // Ex�cution de l'insertion dans la base
        }
    }

	@Override
	public List<String> getTicketsbyEvenement(int id_evenement) throws RemoteException {
		List<String> listBillets=new ArrayList<String>();
		String query="select * from billet where evenement_id = ?";
		try{
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id_evenement);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				listBillets.add("[ numero:"+rs.getInt("id")+" Code_QR:"+rs.getString("code_qr")+" prix:"+rs.getInt("prix")+" statut:"+rs.getString("statut")+" ]");
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listBillets;
	}
    
    


    @Override
    public Billet getBilletById(int id) throws Exception {
        String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre AS eventTitle, p.nom AS participantName " +
                       "FROM billet b " +
                       "JOIN evenement e ON b.evenement_id = e.id " +
                       "JOIN utilisateur p ON b.participant_id = p.id " +
                       "WHERE b.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Billet(
                        rs.getInt("id"),
                        rs.getString("code_qr"),
                        rs.getString("eventTitle"),
                        rs.getString("participantName"),
                        rs.getFloat("prix"),           // Ajout du prix
                        rs.getString("statut"),        // Ajout du statut
                        rs.getInt("participant_id"),   // Ajout de participantId
                        rs.getInt("evenement_id")      // Ajout de evenementId
                    );
                }
            }
        }
        return null; // Aucun billet trouv�
    }

    @Override
    public List<Billet> getAllBillets() throws Exception {
        List<Billet> billets = new ArrayList<>();
        String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre AS eventTitle, p.nom AS participantName " +
                       "FROM billet b " +
                       "JOIN evenement e ON b.evenement_id = e.id " +
                       "JOIN utilisateur p ON b.participant_id = p.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                billets.add(new Billet(
                    rs.getInt("id"),
                    rs.getString("code_qr"),
                    rs.getString("eventTitle"),
                    rs.getString("participantName"),
                    rs.getFloat("prix"),           // Ajout du prix
                    rs.getString("statut"),        // Ajout du statut
                    rs.getInt("participant_id"),   // Ajout de participantId
                    rs.getInt("evenement_id")      // Ajout de evenementId
                ));
            }
        }
        return billets;
    }

    @Override
    public void updateBillet(Billet billet) throws Exception {
        String query = "UPDATE billet SET code_qr = ?, prix = ?, statut = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, billet.getCode());
            pstmt.setFloat(2, billet.getPrix());    // Mise � jour du prix
            pstmt.setString(3, billet.getStatut()); // Mise � jour du statut
            pstmt.setInt(4, billet.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBillet(int id) throws Exception {
        String query = "DELETE FROM billet WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Billet> getTicketsForParticipant(int participantId) throws SQLException {
        List<Billet> tickets = new ArrayList<>();
        String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre AS eventTitle, p.nom AS participantName " +
                       "FROM billet b " +
                       "JOIN evenement e ON b.evenement_id = e.id " +
                       "JOIN utilisateur p ON b.participant_id = p.id " +
                       "WHERE b.participant_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, participantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Billet(
                        rs.getInt("id"),
                        rs.getString("code_qr"),
                        rs.getString("eventTitle"),
                        rs.getString("participantName"),
                        rs.getFloat("prix"),           // Ajout du prix
                        rs.getString("statut"),        // Ajout du statut
                        rs.getInt("participant_id"),   // Ajout de participantId
                        rs.getInt("evenement_id")      // Ajout de evenementId
                    ));
                }
            }
        }
        return tickets;
    }

	@Override
	public boolean deleteTikectbyId(int id_billet) throws Exception {
		// TODO Auto-generated method stub
		boolean t=false;
		String query1 = "update  billet set statut = ?, participant_id = NULL WHERE id = ?";
		String query2 = "update  evenement set capacite = capacite + 1 WHERE id = (select evenement_id from billet where id = ?)";
		PreparedStatement pst1 = connection.prepareStatement(query1);
		pst1.setString(1, "disponible");
		pst1.setInt(2, id_billet);
		if(pst1.executeUpdate()!=0) {
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setInt(1, id_billet);
			if(pst2.executeUpdate()!=0)
				t=true;
		}
		return t;
	}
}
