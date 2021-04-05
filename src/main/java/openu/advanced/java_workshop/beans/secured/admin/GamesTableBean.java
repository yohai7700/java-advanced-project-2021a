package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.CategoryMembersEntity;
import openu.advanced.java_workshop.model.Condition;
import openu.advanced.java_workshop.model.GamesEntity;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class GamesTableBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    private GamesEntity newGame;
    private List<GamesEntity> selectedGames;

    public List<GamesEntity> getSelectedGames() {
        return selectedGames;
    }

    public void setSelectedGames(List<GamesEntity> selectedGames) {
        this.selectedGames = selectedGames;
    }

    public GamesEntity getNewGame() {
        return newGame;
    }

    public Condition[] getConditions() {
        return Condition.values();
    }

    public List<GamesEntity> getGames() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getAllGames = entityManager.createNamedQuery("getAllGames", GamesEntity.class);
        return getAllGames.getResultList();
    }

    public boolean hasSelectedGames() {
        return selectedGames != null && !selectedGames.isEmpty();
    }

    public void openAddGameDialog() {
        newGame = new GamesEntity();
    }

    public void saveGame() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newGame);
        transaction.commit();
        PrimeFaces.current().executeScript("PF('newGameDialog').hide()");
        PrimeFaces.current().ajax().update(":form:messages", ":form:games-data-table");
        addNotification("Game Added");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedGames()) {
            int size = selectedGames.size();
            return size > 1 ? size + " games selected" : "1 game selected";
        }

        return "Delete";
    }

    public void deleteSelectedGames() {
        int removedGamesAmount = selectedGames.size();
        if(!hasSelectedGames()) return;
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (GamesEntity selectedGame : selectedGames) {
            deleteAllCategoryMembersOfGame(entityManager, selectedGame.getId());
            entityManager.remove(entityManager.contains(selectedGame) ? selectedGame : entityManager.merge(selectedGame));
        }
        transaction.commit();
        selectedGames = null;
        addNotification("Deleted " + removedGamesAmount + " game" + (removedGamesAmount == 1 ? "" : "s") + ".");
        PrimeFaces.current().ajax().update("form:messages", "form:games-data-table");
    }

    private void deleteAllCategoryMembersOfGame(EntityManager entityManager, int gameId) {
        TypedQuery<CategoryMembersEntity> findCategoryMembersByGameId = entityManager.createNamedQuery("findCategoryMembersByGameId", CategoryMembersEntity.class);
        findCategoryMembersByGameId.setParameter("gameId", gameId);
        for (CategoryMembersEntity categoryMembersEntity : findCategoryMembersByGameId.getResultList()) {
            entityManager.remove(entityManager.contains(categoryMembersEntity)
                    ? categoryMembersEntity
                    : entityManager.merge(categoryMembersEntity));
        }
    }

    private void addNotification(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
}
