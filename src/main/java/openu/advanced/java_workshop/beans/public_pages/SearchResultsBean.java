package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class SearchResultsBean implements Serializable {

    String searchStr;

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
     * Returns the list of games that their names match the search results
     * @return a list of game entity objects that match the search string
     */
    public List<GamesEntity> getSearchResults() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<GamesEntity> getSearchResults = entityManager.createNamedQuery("search",
                GamesEntity.class);
        getSearchResults.setParameter("name", "%" + searchStr + "%");
        return getSearchResults.getResultList();
    }
}
