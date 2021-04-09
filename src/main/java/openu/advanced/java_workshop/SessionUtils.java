package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.UsersEntity;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session.getAttribute("username") == null) {
            return null;
        }
        return session.getAttribute("username").toString();
    }

    public static UsersEntity getUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return (UsersEntity) session.getAttribute("user");
    }
}
