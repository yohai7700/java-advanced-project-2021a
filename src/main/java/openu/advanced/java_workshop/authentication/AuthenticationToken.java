package openu.advanced.java_workshop.authentication;

import openu.advanced.java_workshop.model.UsersSessionsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;

/**
 * Creates token for authentication of users, using pseudo-random values and encoders
 */

public class AuthenticationToken {

    public static final int TOKEN_LEN = 24;
    private static final SecureRandom secureRandom = new SecureRandom(); // Creates random values
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // Encodes from bytes to strings

    /**
     * Generates a new token for authentication
     * @return a random string token
     */
    private static String generateNewToken() {
        byte[] randomBytes = new byte[TOKEN_LEN];
        secureRandom.nextBytes(randomBytes); // Fills the randomBytes array with random values
        return base64Encoder.encodeToString(randomBytes); // Encodes the byte array into a string
    }

    /**
     * Adds a String token and its matching username to the database
     * @param username the user name we want to add to the database
     * @param token the token we want to add to the database
     */
    private static void addTokenToDB(String username, String token) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");

        // The expiration of the token is one day from login
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        java.sql.Timestamp expiration_date = new java.sql.Timestamp(c.getTimeInMillis());

        // Adding the token and the expiration date to the appropriate user in the data base
        EntityManager entityManager = factory.createEntityManager();
        UsersSessionsEntity session = entityManager.find(UsersSessionsEntity.class, username);
        entityManager.getTransaction().begin();

        // If there's no session with the user, we create a new one with the token and the expiration date
        if (session == null) {
            UsersSessionsEntity newSession = new UsersSessionsEntity(username, token, expiration_date);
            entityManager.persist(newSession);
        }

        // If the session exists, we set the new token and expiration date
        else {
            session.setToken(token);
            session.setExpirationDate(expiration_date);
        }
        entityManager.getTransaction().commit(); // Finally, we add the session to the database
    }

    public static String createSessionTokenForUser(String username) {
        String token = generateNewToken();
        addTokenToDB(username, token);
        return token;
    }

}
