package openu.advanced.java_workshop.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionBean implements Serializable {
    public boolean getIsUserConnected(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute("username") != null;
    }
}
