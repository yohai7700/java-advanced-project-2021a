package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
public class GamePageBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");

    private int getGameId() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String gameIdParameter = req.getParameter("game_id");
        //TODO: remove default category with error message
        return gameIdParameter == null ? 2 : Integer.parseInt(gameIdParameter);
    }

    public GamesEntity getGame() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getGameById = entityManager.createNamedQuery("getGameById", GamesEntity.class);
        getGameById.setParameter("id", getGameId());
        return getGameById.getSingleResult();
    }

    public List<CategoriesEntity> getCategories() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<CategoriesEntity> getCategoriesByGameId = entityManager.createNamedQuery("getCategoriesByGameId", CategoriesEntity.class);
        getCategoriesByGameId.setParameter("gameId", getGameId());
        return getCategoriesByGameId.getResultList();
    }

    public List<GamesEntity> getRecommendations() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getCategoriesByGameId = entityManager.createNamedQuery("getRecommendationsByGameId", GamesEntity.class);
        getCategoriesByGameId
                .setParameter("gameId", getGameId())
                .setMaxResults(5);
        return getCategoriesByGameId.getResultList();
    }

    public void openRecommendationPage() throws IOException {
        Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String recommendationId = params.get("recommendation_id");
        String url = "game-page.xhtml?game_id=" + recommendationId;
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
