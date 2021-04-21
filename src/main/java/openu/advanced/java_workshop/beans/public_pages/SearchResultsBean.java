package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Bean class for the search-results.xhtml file. Gives the page access to the search results
 */

@Named
@SessionScoped
public class SearchResultsBean implements Serializable {

    List<GamesEntity> searchResults;

    /**
     * Returns the list of games that their names match the search results
     * @return a list of game entity objects that match the search string
     */
    public List<GamesEntity> getSearchResults(String str) {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<GamesEntity> getSearchResults = entityManager.createNamedQuery("searchGames",
                GamesEntity.class);
        getSearchResults.setParameter("name", str);
        searchResults = getSearchResults.getResultList();
        return searchResults;
    }

    /**
     * Returns the number of results for the search
     * @return an integer that holds the number of results for the latest search
     */
    public int numResults() { return searchResults.size(); }
}
