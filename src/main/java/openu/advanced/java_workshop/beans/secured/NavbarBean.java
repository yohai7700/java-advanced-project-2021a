package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.beans.LoginBean;

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

    public void handleLogout() throws IOException {
        loginBean.logout();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
    }
}
