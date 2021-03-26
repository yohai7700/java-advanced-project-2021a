package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;
import openu.advanced.java_workshop.model.ImagesRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class GamePageBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");
    @Inject
    private ShoppingCartBean shoppingCartBean;

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


    public String getGameImagePath(){
        return GamesEntity.getImagePath(getGameId());
    }

    public boolean getGameImageSaved(){
        return ImagesRepository.isExists(getGameImagePath());
    }

    public InputStream getGameImage() throws IOException {
        return ImagesRepository.retrieveImage(getGameImagePath());
    }

    public boolean getGameIsInCart(){
        return shoppingCartBean.getGames().contains(getGameId());
    }

    public void addToCart(){
        GamesEntity game = getGame();

        shoppingCartBean.getGames().add(game.getId());
        game.setStock(game.getStock() - 1);
    }

    public void removeFromCart(){
        GamesEntity game = getGame();

        shoppingCartBean.getGames().remove(game.getId());
        game.setStock(game.getStock() + 1);
    }
}
