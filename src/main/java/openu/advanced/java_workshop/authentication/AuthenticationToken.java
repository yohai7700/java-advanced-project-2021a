package openu.advanced.java_workshop.authentication;

import openu.advanced.java_workshop.model.UsersSessionsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;

public class AuthenticationToken {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private static void addTokenToDB(String username, String token) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");

        // getting the expiration date - one date from login
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        java.sql.Timestamp expiration_date = new java.sql.Timestamp(c.getTimeInMillis());


        EntityManager entityManager = factory.createEntityManager();
        UsersSessionsEntity session = entityManager.find(UsersSessionsEntity.class, username);
        entityManager.getTransaction().begin();
        if (session == null){ // add new user to sessions table
            UsersSessionsEntity newSession = new UsersSessionsEntity(username, token, expiration_date);
            entityManager.persist(newSession);
        }
        else{// update existing user in sessions table
            session.setToken(token);
            session.setExpirationDate(expiration_date);
        }
        entityManager.getTransaction().commit();
    }

    public static String createSessionTokenForUser(String username) {
        String token = generateNewToken();
        addTokenToDB(username, token);
        return token;
    }

}
