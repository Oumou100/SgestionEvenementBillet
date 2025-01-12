package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection connection;

    // M�thode pour obtenir une connexion
    public static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) { // V�rifier si la connexion est ferm�e
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_evenement_billet",
                "root",
                "Kone76#21$"
            );
        }
        return connection;
    }
}
