package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@ManagedBean
public class GamePageBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");

    private int getGameId() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String gameIdParameter = req.getParameter("game_id");
        //TODO: remove default category with error message
        return gameIdParameter == null ? 1 : Integer.parseInt(gameIdParameter);
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
}
