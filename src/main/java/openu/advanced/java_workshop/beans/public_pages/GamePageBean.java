package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.beans.secured.ShoppingCartBean;
import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;
import openu.advanced.java_workshop.model.ImagesRepository;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class GamePageBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");
    public static final int NOT_FOUND_GAME_ID = -1;

    @Inject
    private ShoppingCartBean shoppingCartBean;

    /**
     * Returns the current page game id by the query parameters on the context URL.
     * @return game id of current page, or -1 of wasn't found
     */
    public int getGameId() {
        Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String gameIdParameter = parameterMap.get("game_id");
        return gameIdParameter == null ? NOT_FOUND_GAME_ID : Integer.parseInt(gameIdParameter);
    }

    public GamesEntity getGame() {
        int gameId = getGameId();
        if(gameId == NOT_FOUND_GAME_ID) return null;
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        return entityManager.find(GamesEntity.class, getGameId());
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
        Map<String,String> params =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String recommendationId = params.get("recommendation_id");
        String url = "/public-pages/game-page.xhtml?game_id=" + recommendationId;
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

    public boolean getIsGameInCart(){
        return shoppingCartBean.getGames().contains(getGame());
    }

    public void addToCart() {
        shoppingCartBean.addGame(getGame());
    }

    public void removeFromCart(){
        shoppingCartBean.removeGame(getGame());
    }

    public boolean isUserSignedIn(){
        return SessionUtils.getUser() != null;
    }
}
