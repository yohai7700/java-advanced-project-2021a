package openu.advanced.java_workshop.beans;

import openu.advanced.java_workshop.model.UsersEntity;
import org.primefaces.event.FlowEvent;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

/**
 * Bean class for the register.xhtml file. Manages the logics of registration
 */

@Named
@ViewScoped
public class RegisterBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");
    private final UsersEntity user = new UsersEntity();

    /**
     * Gives xhtml files (and especially register.xhtml) access to the user attribute
     * @return the current user
     */
    public UsersEntity getUser() {
        return user;
    }

    /**
     * Registers a new user
     * @return login if the user registered successfully and
     * an empty String otherwise (if the username is already taken)
     */
    public String register(){

        /* Finding if there's a user in the database with the same username
        that the guest tried to register with */
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        UsersEntity existingUser = entityManager.find(UsersEntity.class, user.getUsername());

        if (existingUser == null) { // A unique username - we'll add the new user to the database
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return "login";
        }
        // The username is already taken
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Username is already taken",
                        "Please pick another username"));
        return "";
    }
}

