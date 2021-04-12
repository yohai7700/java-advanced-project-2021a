package openu.advanced.java_workshop.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * A bean class for session related functions
 */

@Named
@SessionScoped
public class SessionBean implements Serializable {

    /**
     * Finds if the current session is being run by a user or a guest
     * @return true if the current session is being run by a user and false if it is being run by a guest
     */
    public boolean getIsUserConnected()
    {
        // Gets the current session
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        // If the user attribute is null, the session is run by a guest and not a user
        return session.getAttribute("username") != null;
    }
}
