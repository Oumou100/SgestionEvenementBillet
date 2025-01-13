/*package dao.impl;

import dao.UtilisateurDAO;
import metier.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private final Connection connection;

    // Constructeur pour injecter une connexion à la base de données
    public UtilisateurDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) throws Exception {
        String query = "INSERT INTO utilisateur (nom, email, mot_de_passe, role, is_authentified) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setBoolean(5, utilisateur.isAuthentified()); // Nouveau champ
            pstmt.executeUpdate();
        }
    }

    @Override
    public Utilisateur getUtilisateurById(int id) throws Exception {
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role"),
                        rs.getBoolean("is_authentified") // Nouveau champ
                    );
                }
            }
        }
        return null; // Si aucun utilisateur trouvé
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() throws Exception {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("role"),
                    rs.getBoolean("is_authentified") // Nouveau champ
                ));
            }
        }
        return utilisateurs;
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) throws Exception {
        String query = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ?, role = ?, is_authentified = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setBoolean(5, utilisateur.isAuthentified()); // Nouveau champ
            pstmt.setInt(6, utilisateur.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteUtilisateur(int id) throws Exception {
        String query = "DELETE FROM utilisateur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}*/
package dao.impl;

import dao.UtilisateurDAO;
import metier.Utilisateur;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private final Connection connection;
	
    
    // Constructeur pour injecter une connexion ï¿½ la base de donnï¿½es
    public UtilisateurDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) throws Exception {
        String query = "INSERT INTO utilisateur (nom, email, mot_de_passe, role, is_authentified) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setBoolean(5, utilisateur.isAuthentified()); // Nouveau champ
            pstmt.executeUpdate();
        }
    }

    @Override
    public Utilisateur getUtilisateurById(int id) throws Exception {
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role"),
                        rs.getBoolean("is_authentified") // Nouveau champ
                    );
                }
            }
        }
        return null; // Si aucun utilisateur trouvï¿½
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() throws Exception {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("role"),
                    rs.getBoolean("is_authentified") // Nouveau champ
                ));
            }
        }
        return utilisateurs;
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) throws Exception {
        String query = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ?, role = ?, is_authentified = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setBoolean(5, utilisateur.isAuthentified()); // Nouveau champ
            pstmt.setInt(6, utilisateur.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteUtilisateur(int id) throws Exception {
        String query = "DELETE FROM utilisateur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

	@Override
	public boolean updateNameUser(int id_user,String newName) throws Exception {
		boolean t=false;
		String query = "UPDATE utilisateur SET nom = ? where id=?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1, newName);
			pstmt.setInt(2, id_user);
			if(pstmt.executeUpdate()!=0)
				t=true;
		}
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateEmailUser(int id_user,String newEmail) throws Exception {
		boolean t=false;
		String query = "UPDATE utilisateur SET email = ? where id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1, newEmail);
			pstmt.setInt(2, id_user);
			if(pstmt.executeUpdate()!=0)
				t=true;
		}
		return t;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updatePasswordUser(int id_user,String newPwd) throws Exception {
		boolean t=false;
		String query = "UPDATE utilisateur SET password = ? where id=?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1, newPwd);
			pstmt.setInt(2, id_user);
			if(pstmt.executeUpdate()!=0)
				t=true;
		}
		return t;
		// TODO Auto-generated method stub
		
	}

	public boolean updateRoleUser(int id_user, String newRole) throws Exception {

	    boolean success = false;

	    int id_evenement=0;

	    	String query1 = "UPDATE utilisateur SET role = ? WHERE id = ?";

		    String query2 = "UPDATE billet SET statut = ?, participant_id = NULL WHERE participant_id = ?";

		    String query3 = "select evenement_id from billet where participant_id = ?";

		    try(PreparedStatement pst1 = connection.prepareStatement(query3)){

		    	pst1.setInt(1, id_user);

           	 ResultSet rs=pst1.executeQuery();

           	 if(rs.next()) {

           		  id_evenement=rs.getInt("evenement_id");

           	 }

		    }

	    try (PreparedStatement pstmt = connection.prepareStatement(query1)) {

	        pstmt.setString(1, newRole);

	        pstmt.setInt(2, id_user);

	        

	        if (pstmt.executeUpdate() != 0) {  // Update user role successful

	            try (PreparedStatement pst = connection.prepareStatement(query2)) {

	                pst.setString(1, "disponible");

	                pst.setInt(2, id_user);

	                int nb_billet_supprimer=pst.executeUpdate();

	                System.out.print(nb_billet_supprimer);

	                if (nb_billet_supprimer != 0) {  // Update billets successful

	            		 PreparedStatement st =connection.prepareStatement("update evenement set capacite = capacite + ? where id = ?");

	            		 st.setInt(1, nb_billet_supprimer); 

	            		 st.setInt(2, id_evenement);

	            		 if(st.executeUpdate()!=0)

	            			 success = true;

	            		 

	            	 }

	                else if (nb_billet_supprimer==0)

	                	success=true;

	                else {

	                    System.out.println("Erreur dans la mise à jour des billets");

	                }

	            	 

	             } 

	                }

	        else if(pstmt.executeUpdate() == 0) 

	            success=true;

	                

	            }

	         

	      

	     catch (SQLException e) {

	        e.printStackTrace();  // Log exception or handle it as needed

	    }

	 

	    

	    return success;

	}

	@Override
	public List<String> mesAchats(int id_user) throws Exception {
	    List<String> listeDeMesAchats = new ArrayList<>();
	    String query = "SELECT b.id, b.code_qr, b.prix, b.statut, e.titre, e.description, e.lieu, e.date " +
	                   "FROM billet b " +
	                   "JOIN evenement e ON b.evenement_id = e.id " +
	                   "WHERE b.participant_id = ?";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, id_user);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String achatDetails = String.format(
	                    "[Id:%s Code-QR:%s Titre:%s Description:%s Lieu:%s Date:%s prix:%d statut:%s ]",
	                    rs.getInt("id"),
	                    rs.getString("code_qr"),
	                    rs.getString("titre"),
	                    rs.getString("description"),
	                    rs.getString("lieu"),
	                    rs.getDate("date"),
	                    rs.getInt("prix"),
	                    rs.getString("statut")
	                );
	                listeDeMesAchats.add(achatDetails);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    // Optional: print the list to the console for debugging

	    
	    return listeDeMesAchats;
	}

	@Override
	public List<String> mesEvenement(int id_user) throws Exception{
		List<String>listeDeMesEvenement=new ArrayList<String>();
		// TODO Auto-generated method stub
		String query2="select * from evenement where organisateur_id = (select id from utilisateur where id=?)";
		String query="select count (id) from billet where statut = ? ";
		int nb_reserver=0;
		try{
				PreparedStatement pstmt2 = connection.prepareStatement(query2);
				PreparedStatement pst =connection.prepareStatement(query);
				pstmt2.setInt(1, id_user);
				pst.setString(1, "reserver");
				ResultSet rs2=pstmt2.executeQuery();
				ResultSet rs=pst.executeQuery();
				while(rs2.next()) {
					while(rs.next()) {
						nb_reserver=rs.getInt(1);
					listeDeMesEvenement.add("[ id:"+rs2.getString("id")+" Titre:"+rs2.getString("titre")+" Description:"+rs2.getString("description")+" Lieu:"+rs2.getString("lieu")+" Date:"+rs2.getDate("date")+" prix:"+rs2.getInt("prix")+" capacite:"+rs2.getString("capacite")+" billet reservÃ©s:"+nb_reserver+" ]");
				}
				}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeDeMesEvenement;
	}

	@Override
	public boolean acheterBillet(int id_user, int id_billet) throws RemoteException {
		// TODO Auto-generated method stub
		boolean t=false;
		String query = "UPDATE billet SET statut = ?, participant_id = ? where id = ? ";
		String query2 = "UPDATE evenement SET capacite = capacite - 1   where id = (select evenement_id from billet where id = ?) ";
		try{
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "reserve");
			pstmt.setInt(2, id_user);
			pstmt.setInt(3, id_billet);
			if(pstmt.executeUpdate()!=0) {
				PreparedStatement pstmt2=connection.prepareStatement(query2);
				pstmt2.setInt(1, id_billet);
				if(pstmt2.executeUpdate()!=0)
				t=true;
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
}

