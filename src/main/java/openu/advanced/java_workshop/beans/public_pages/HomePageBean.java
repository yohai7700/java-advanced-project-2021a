package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Bean class for the index.xhtml file. Manages the logics of the homepage
 */

@Named
@RequestScoped
public class HomePageBean {

    // Used to run queries
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");

    /**
     * Get all of the categories in the database, to show their games in the homepage
     * @return a list of all the categories
     */
    public List<CategoriesEntity> getCategories() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<CategoriesEntity> getCategories = entityManager.createNamedQuery("findAllCategories", CategoriesEntity.class);
        return getCategories.getResultList();
    }

    private static final int MAX_GAMES_IN_CAROUSEL = 6;

    /**
     * Fetches all of the games in the category with the given id
     * @param id the id of the category that we are looking for it's games
     * @return a list of the category's games
     */
    public List<GamesEntity> getGames(int id) {
        // Getting the query
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> findGamesByCategoryId = entityManager.createNamedQuery("findGamesByCategoryId", GamesEntity.class);

        // Shows at most a constant number of games in the category's carousel
        findGamesByCategoryId.setMaxResults(MAX_GAMES_IN_CAROUSEL);
        findGamesByCategoryId.setParameter("categoryId", id);
        return findGamesByCategoryId.getResultList();
    }
}
