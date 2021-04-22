package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.beans.LoginBean;
import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class NavbarBean implements Serializable {
    private static final int MAX_CATEGORIES = 5;
    private static final String SEARCH_RESULTS_URL = "/public-pages/search-results.xhtml";

    String searchString = "";
    @Inject
    LoginBean loginBean;

    /**
     * Gives access to the search string, entered in the search section in the navbar
     * @return the current search string
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Enables modification of the searchString attribute, in order to perform a new search
     * @param searchString the new search string
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
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
     * Redirects to search-results.xhtml with the search
     */
    public void openSearchResults() throws IOException {
        String searchToken = URLEncoder.encode(searchString, "UTF-8");
        String redirectUrl = SEARCH_RESULTS_URL + "?faces-redirect=true&" + "search-token=" + searchToken;
        FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
    }
}
