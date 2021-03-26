package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.UsersEntity;
import openu.advanced.java_workshop.model.UsersSessionsEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String password, username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private boolean validate(String username, String password){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");
        EntityManager entityManager = factory.createEntityManager();
        UsersEntity session = entityManager.find(UsersEntity.class, username);


        //returning true whether the username exists and password matching
        return session != null && session.getPassword().equals(password);
    }

    public String giveUserAccess() {
        boolean valid = validate(getUsername(), getPassword());
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", getUsername());
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Password",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }

}




