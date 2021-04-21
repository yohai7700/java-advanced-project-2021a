package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.beans.LoginBean;
import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.List;

/**
 * Bean class for the navbar.xhtml file. Manages the logics of the navigation bar
 */

@Named
@SessionScoped
public class NavbarBean implements Serializable {
    private static final int MAX_CATEGORIES = 5;

    @Inject
    LoginBean loginBean;
    String searchStr = "";

    /**
     * Gives access to the search string, entered in the search section in the navbar
     * @return the current search string
     */
    public String getSearchStr() {
        return searchStr;
    }

    /**
     * Enables modification of the searchString attribute, in order to perform a new search
     * @param searchStr the new search string
     */
    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    /**
     * Returns the user who runs the current session
     * @return a user object matching the user who runs this session
     */
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

    /**
     * Get all of the categories in the database, max results is 5
     * @return a list of all the categories (limited to max)
     */
    public List<CategoriesEntity> getCategories() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<CategoriesEntity> getCategories = entityManager.createNamedQuery("findAllCategories",
                CategoriesEntity.class);
        return getCategories.setMaxResults(MAX_CATEGORIES).getResultList();
    }

    /**
     * Redirects to search-results.xhtml
     */
    public String search() throws IOException {
        return "/public-pages/search-results.xhtml?faces-redirect=true&token=" + URLEncoder.encode(searchStr, "UTF-8");
    }
}
