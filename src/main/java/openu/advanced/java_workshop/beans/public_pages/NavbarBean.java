package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.beans.LoginBean;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Bean class for the navbar.xhtml file. Manages the logics of the navigation bar
 */

@Named
@SessionScoped
public class NavbarBean implements Serializable {
    @Inject
    LoginBean loginBean;

    public UsersEntity getUser() {
        return SessionUtils.getUser();
    }

    /**
     * Handles the logout (called by clicking the logout button in the navigation bar
     * @throws IOException required for the redirect method
     */
    public void handleLogout() throws IOException {
        loginBean.logout();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
    }
}
