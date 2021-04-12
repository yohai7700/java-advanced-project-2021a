package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Exception in case there's no existing user with the given parameters (depends on the usage in the various methods)
 */
class NotExistingUserException extends Exception {}

/**
 * This bean handles management of the users-management.xhtml page
 */

@Named
@RequestScoped
public class UsersManagementBean implements Serializable {

    /**
     * Finds all the users of the website
     * @return a list of all the created users
     */
    public List<UsersEntity> getUsers(){
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<UsersEntity> findAllUsers = entityManager.createNamedQuery("findAllUsers", UsersEntity.class);
        return findAllUsers.getResultList();
    }

    /**
     * Gives the user with the given username admin permissions
     * @param username the name of the user that will get admin permissions
     * @throws NotExistingUserException if there's no user with the given username
     */
    public void makeUserAdmin(String username) throws NotExistingUserException {
        // Gets the user with the given username from the database
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        UsersEntity user = entityManager.find(UsersEntity.class, username);

        // If the user doesn't exist, we throw an exception
        if(user == null)
            throw new NotExistingUserException();

        // Sets the user with the given username as admin
        user.setIsAdmin(true);
        transaction.commit();
    }
}
