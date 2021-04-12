package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created By: Yohai Mazuz
 * This bean handles management of the category-page.xhtml page
 */
@Named
@RequestScoped
public class CategoryPageBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");

    public int getCategoryId() {
        HttpServletRequest req =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String categoryIdParameter = req.getParameter("category_id");
        return categoryIdParameter == null ? NOT_FOUND_CATEGORY_ID : Integer.parseInt(categoryIdParameter);
    }

    /**
     * Returns the category shown by category-page.xhtml
     * @return the categories entity shown in the page
     */
    public CategoriesEntity getCategory() {
        // Calls the query findCategoryById, defined in CategoriesEntity, with the id of the page's category
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<CategoriesEntity> getCategoryById =
                entityManager.createNamedQuery("findCategoryById", CategoriesEntity.class);
        getCategoryById.setParameter("id", getCategoryId());
        return getCategoryById.getSingleResult();
    }

    /**
     * Finds all the games that belong to the category shown in this page
     * @return a list of the games in the current category
     */
    public List<GamesEntity> getGames() {
        // Calls the query findGamesByCategoryId, defined in CategoriesEntity, with the id of the page's category
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<GamesEntity> getCategoryById =
                entityManager.createNamedQuery("findGamesByCategoryId", GamesEntity.class);
        getCategoryById.setParameter("categoryId", getCategoryId());
        return getCategoryById.getResultList();
    }

    public void openGamePage(int gameId) throws IOException {
        String url = "game-page.xhtml?game_id=" + gameId;
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
