package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.UsersEntity;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * This class offers utilities for sessions, including access to session's related object
 */

public class SessionUtils {

    /**
     * Gives access to the current session
     * @return the current session
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    /**
     * Invalidates the session of the user, behaves like a user logout.
     */
    public static void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    /**
     * Finds the name of the user that runs the session
     * @return the name of the user who runs the session
     */
    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false); // Finds the current session
        // If the session has no username, we return null (we cannot use toString() on null)
        if (session.getAttribute("username") == null)
            return null;
        return session.getAttribute("username").toString();
    }

    /**
     * Gets the user who runs this session
     * @return the user that runs the current session
     */
    public static UsersEntity getUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false); // Finds the current session
        return (UsersEntity) session.getAttribute("user"); // Gets the user attribute
    }
}
