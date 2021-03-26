package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.UsersEntity;
import openu.advanced.java_workshop.model.UsersSessionsEntity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
@RequestScoped
public class RegisterBean implements Serializable {
    private UsersEntity user = new UsersEntity();

    public UsersEntity getUser() {
        return user;
    }

    public String register(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");
        EntityManager entityManager = factory.createEntityManager();
        UsersEntity session = entityManager.find(UsersEntity.class, user.getUsername());

        if (session == null){ // add new user to users table
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
