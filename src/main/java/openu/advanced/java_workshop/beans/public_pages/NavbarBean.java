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

@Named
@SessionScoped
public class NavbarBean implements Serializable {
    @Inject
    LoginBean loginBean;

    public UsersEntity getUser() {
        return SessionUtils.getUser();
    }

    public void handleLogout() throws IOException {
        loginBean.logout();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
    }
}
