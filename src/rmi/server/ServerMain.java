package rmi.server;

import rmi.impl.EventServiceRemote;
import rmi.impl.TicketServiceRemote;
import rmi.impl.UserServiceRemote;
import utils.DBConnection;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;

import dao.impl.BilletDAOImpl;

public class ServerMain {
    public static void main(String[] args) {
        try {
            // D�marrer le registre RMI (port 1099 par d�faut)
            LocateRegistry.createRegistry(1099);
            
            // Obtenir une connexion � la base de donn�es
            Connection connection = DBConnection.getConnection();
            
            
            // Cr�er et enregistrer le service EventService
            EventServiceRemote eventService = new EventServiceRemote(connection);
            Naming.rebind("rmi://localhost:1099/EventService", eventService);
            System.out.println("Service RMI 'EventService' d�marr� !");
            
            // Cr�er et enregistrer le service UserService
            UserServiceRemote userService = new UserServiceRemote(connection);
            Naming.rebind("rmi://localhost:1099/UserService", userService);
            System.out.println("Service RMI 'UserService' d�marr� !");
            
            // Cr�er et enregistrer le service TicketService
            TicketServiceRemote ticketService = new TicketServiceRemote(new BilletDAOImpl(connection));
            Naming.rebind("rmi://localhost:1099/TicketService", ticketService);
            System.out.println("Service RMI 'TicketService' d�marr� !");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
