package openu.advanced.java_workshop.beans;

import openu.advanced.java_workshop.SessionUtils;
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

/**
 * Bean class for the login.xhtml file. Manages the logics of logging in
 */

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String password, username;
    private UsersEntity user;

    /**
     * Gives xhtml files (and especially login.xhtml) access to the user attribute
     * @return the user who logged into the website
     */
    public UsersEntity getUser() {
        return user;
    }

    /**
     * Gives other classes access to the password attribute
     * @return the password of the category
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gives other classes the ability to modify the password attribute
     * @param password the new name of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gives other files access to the username attribute
     * @return the name of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gives other classes the ability to modify the username attribute
     * @param username the new name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Validates if the username and password given during the login are valid -
     * there is a user in the database with those username and password
     * @param username the username entered during login
     * @param password the password entered during login
     * @return the user object matching the given username and password if the values are valid
     * and null otherwise
     */
    private UsersEntity validate(String username, String password){
        // Finding a user with the same username in the database
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");
        EntityManager entityManager = factory.createEntityManager();
        UsersEntity user = entityManager.find(UsersEntity.class, username);

        /* If user is null, there is no user with the given password.
        if it's not null, the passwords should match */
        return user != null && user.getPassword().equals(password) ? user : null;
    }

    /**
     * Validates the login data. If it's valid, we add the logged user to session
     * If not, we show an error message and give the guest another chance
     * @return the relative path to redirect to
     */
    public String login() {
        // Validates the login data
        UsersEntity user = validate(getUsername(), getPassword());

        if (user != null) { // The login data is valid
            this.user = user;
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", getUsername());
            return "secured/index.xhtml?faces-redirect=true";
        }

        // The login data isn't valid
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Incorrect Username and/or Password",
                        "Please enter correct username and Password"));
        return "login";
    }

    /**
     * Logs out from the website - will redirect to the login page
     * @return login - to redirect to the login page
     */
    public String logout() {
        SessionUtils.invalidateSession();
        return "login";
    }
}