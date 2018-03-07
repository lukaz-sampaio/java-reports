package lab.basics.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucas
 */
public class DatabaseConnection {

    private static final Logger LOG = Logger.getLogger(DatabaseConnection.class.getName());
    
    private DatabaseConnection(){}

    private static Connection instance;

    private Connection getConnection() {
        Connection conn = null;
        final String DRIVER = "org.postgresql.Driver";
        final String USER = "postgres";
        final String PASSWORD = "f1d1@s";
        
        try {
            Class.forName(DRIVER);
            conn = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5432/java_web_basics", 
                            USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }

    public static Connection getInstance() {
        if (instance == null) {
            DatabaseConnection db = new DatabaseConnection();
            instance = db.getConnection();
        }
        return instance;
    }
}
