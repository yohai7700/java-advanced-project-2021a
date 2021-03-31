package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String password, username;

    public UsersEntity getUser() {
        return user;
    }

    private UsersEntity user;

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

    private UsersEntity validate(String username, String password){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");
        EntityManager entityManager = factory.createEntityManager();
        UsersEntity user = entityManager.find(UsersEntity.class, username);

        return user != null && user.getPassword().equals(password) ? user : null;
    }

    public String login() {
        UsersEntity user = validate(getUsername(), getPassword());
        if (user != null) {
            this.user = user;
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", getUsername());
            return "secured/index.xhtml?faces-redirect=true";
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
        SessionUtils.invalidateSession();
        return "login";
    }
}