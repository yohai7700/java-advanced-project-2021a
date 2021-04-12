package openu.advanced.java_workshop;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class WorkshopDatabase {
    static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");

    /**
     * Connects to the database and returns the connection
     * @return the connection to the database
     * @throws NamingException caused by the lookup function
     * @throws SQLException in case the connection attempt failed
     */
    public static Connection getConnection() throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/workshop");
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to obtain connection.");
        }
        return connection;
    }

    /**
     * Creating easy access to the entity manager factory, in order to create entity managers
     * @return the entity manager factory
     */
    public static EntityManagerFactory getEntityManagerFactory(){
        return ENTITY_MANAGER_FACTORY;
    }
}
