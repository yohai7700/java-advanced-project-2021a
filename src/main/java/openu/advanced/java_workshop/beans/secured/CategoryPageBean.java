package openu.advanced.java_workshop.beans.secured;

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
    public static final int NOT_FOUND_CATEGORY_ID = -1;

    /**
     * Returns the current page category id by the query parameters on the context URL.
     * @return category id of current page, or -1 of wasn't found
     */
    public int getCategoryId() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String categoryIdParameter = req.getParameter("category_id");
        return categoryIdParameter == null ? NOT_FOUND_CATEGORY_ID : Integer.parseInt(categoryIdParameter);
    }

    public CategoriesEntity getCategory(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<CategoriesEntity> getCategoryById = entityManager.createNamedQuery("findCategoryById", CategoriesEntity.class);
        getCategoryById.setParameter("id", getCategoryId());
        return getCategoryById.getSingleResult();
    }

    public List<GamesEntity> getGames(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getCategoryById = entityManager.createNamedQuery("findGamesByCategoryId", GamesEntity.class);
        getCategoryById.setParameter("categoryId", getCategoryId());
        return getCategoryById.getResultList();
    }

    public void openGamePage(int gameId) throws IOException {
        String url = "game-page.xhtml?game_id=" + gameId;
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
