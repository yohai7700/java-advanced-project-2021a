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
        Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String gameIdParameter = parameterMap.get("game_id");
        //TODO: remove default category with error message
        return gameIdParameter == null ? 2 : Integer.parseInt(gameIdParameter);
    }

    public GamesEntity getGame() {
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

    public boolean getIsGameInCart(){
        return shoppingCartBean.getGames().contains(getGameId());
    }

    public void addToCart(){
        shoppingCartBean.getGames().add(getGameId());
        System.out.println(shoppingCartBean.getGames());
    }

    public void removeFromCart(){
        shoppingCartBean.getGames().remove(getGameId());
    }

    public void onLoad(){
        System.out.println("TEST");
    }
}
