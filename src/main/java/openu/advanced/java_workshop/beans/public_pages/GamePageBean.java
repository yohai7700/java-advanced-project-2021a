package openu.advanced.java_workshop.beans.public_pages;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.beans.secured.ShoppingCartBean;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Bean class for the game-page.xhtml file. Manages the logics of the game page
 */

@Named
@RequestScoped
public class GamePageBean implements Serializable {
    public static final int NOT_FOUND_GAME_ID = -1;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");
    @Inject
    private ShoppingCartBean shoppingCartBean; // To add the game to the shopping cart

    /**
     * Returns the current page game id by the query parameters on the context URL.
     *
     * @return game id of current page, or -1 of wasn't found
     */
    public int getGameId() {
        Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String gameIdParameter = parameterMap.get("game_id");
        return gameIdParameter == null ? NOT_FOUND_GAME_ID : Integer.parseInt(gameIdParameter);
    }

    /**
     * Returns the game that we are in it's page
     * @return the game
     */
    public GamesEntity getGame() {
        int gameId = getGameId();
        if (gameId == NOT_FOUND_GAME_ID) return null;
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        return entityManager.find(GamesEntity.class, getGameId());
    }

    /**
     * Fetches all of the categories that are connected to the page's game
     * @return a list of all the categories
     */
    public List<CategoriesEntity> getCategories() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<CategoriesEntity> getCategoriesByGameId = entityManager.createNamedQuery("getCategoriesByGameId", CategoriesEntity.class);
        getCategoriesByGameId.setParameter("gameId", getGameId());
        return getCategoriesByGameId.getResultList();
    }

    private static final int MAX_RECOMMENDATIONS = 5;
    /**
     * Returns recommendations depending of the page's game
     * @return a list of recommended games
     */
    public List<GamesEntity> getRecommendations() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getRecommendationsByGameId =
                entityManager.createNamedQuery("getRecommendationsByGameId", GamesEntity.class);
        getRecommendationsByGameId.setParameter("gameId", getGameId()).setMaxResults(MAX_RECOMMENDATIONS);
        return getRecommendationsByGameId .getResultList();
    }

    /**
     * Open the game page of the game clicked in the recommendations scroll bar
     * @throws IOException required in case of and error while redirecting to the game's page
     */
    public void openRecommendationPage() throws IOException {
        Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String recommendationId = params.get("game_id");
        String url = "game-page.xhtml?game_id=" + recommendationId;
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    /**
     * Returns the path of the game's image
     * @return the image's path
     */
    public String getGameImagePath(){
        return GamesEntity.getImagePath(getGameId());
    }

    /**
     * Detects if there's a saved image for this game
     * @return true if there's an image for this game and false otherwise
     */
    public boolean getGameImageSaved(){
        return ImagesRepository.isExists(getGameImagePath());
    }

    /**
     * Fetches the game's image
     * @return the image of the game
     * @throws IOException in case of an IO error
     */
    public InputStream getGameImage() throws IOException {
        return ImagesRepository.retrieveImage(getGameImagePath());
    }

    /**
     * Checks whether this game is in the shopping cart
     * @return true if the game is in the shopping cart and false otherwise
     */
    public boolean getIsGameInCart(){
        return shoppingCartBean.getGames().contains(getGame());
    }

    /**
     * Adds this game to the shopping cart
     */
    public void addToCart() {
        shoppingCartBean.addGame(getGame());
    }

    /**
     * Removes this game from the shopping cart
     */
    public void removeFromCart(){
        shoppingCartBean.removeGame(getGame());
    }

    /**
     * Finds if the session is run by a user or a guest
     * @return true if the session is run by a user and false otherwise
     */
    public boolean isUserSignedIn(){
        return SessionUtils.getUser() != null;
    }
}
