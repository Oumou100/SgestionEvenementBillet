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
            // Démarrer le registre RMI (port 1099 par défaut)
            LocateRegistry.createRegistry(1099);
            
            // Obtenir une connexion à la base de données
            Connection connection = DBConnection.getConnection();
            
            
            // Créer et enregistrer le service EventService
            EventServiceRemote eventService = new EventServiceRemote(connection);
            Naming.rebind("rmi://localhost:1099/EventService", eventService);
            System.out.println("Service RMI 'EventService' démarré !");
            
            // Créer et enregistrer le service UserService
            UserServiceRemote userService = new UserServiceRemote(connection);
            Naming.rebind("rmi://localhost:1099/UserService", userService);
            System.out.println("Service RMI 'UserService' démarré !");
            
            // Créer et enregistrer le service TicketService
            TicketServiceRemote ticketService = new TicketServiceRemote(new BilletDAOImpl(connection));
            Naming.rebind("rmi://localhost:1099/TicketService", ticketService);
            System.out.println("Service RMI 'TicketService' démarré !");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
