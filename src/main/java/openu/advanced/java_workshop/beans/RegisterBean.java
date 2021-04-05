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

@Named
@ViewScoped
public class RegisterBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");
    private final UsersEntity user = new UsersEntity();

    public UsersEntity getUser() {
        return user;
    }

    public String register(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        UsersEntity existingUser = entityManager.find(UsersEntity.class, user.getUsername());

        if (existingUser == null){ // add new user to users table
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return "login";
        }
        else{//
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Username is already taken",
                            "Please pick another username"));
            return "";
        }
    }
}

