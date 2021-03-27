package openu.advanced.java_workshop;

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
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String categoryIdParameter = req.getParameter("category_id");
        //TODO: remove default category with error message
        return categoryIdParameter == null ? 4 :Integer.parseInt(categoryIdParameter);
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
        System.out.println("gameId=" + gameId);
        String url = "game-page.xhtml?game_id=" + gameId;
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
