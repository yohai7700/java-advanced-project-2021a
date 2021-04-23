package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.CategoryMembersEntity;
import openu.advanced.java_workshop.model.Condition;
import openu.advanced.java_workshop.model.GamesEntity;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

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

/**
 * This bean handles management of the games-table.xhtml page
 */
@Named
@ViewScoped
public class GamesTableBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    private GamesEntity newGame;
    private GamesEntity changedGame;
    // The game in the current game image dialog
    private GamesEntity changingImageGame;
    private List<GamesEntity> selectedGames;

    /**
     * Gets the list of all the selected games
     * @return the list of selected games
     */
    public List<GamesEntity> getSelectedGames() {
        return selectedGames;
    }

    /**
     * Modifies the list of selected games
     * @param selectedGames the new list of selected games
     */
    public void setSelectedGames(List<GamesEntity> selectedGames) {
        this.selectedGames = selectedGames;
    }

    /**
     * Returns the new game one of the admins wants to add to the website
     * @return the new game as a GamesEntity
     */
    public GamesEntity getNewGame() {
        return newGame;
    }

    /**
     * Returns the new game one of the admins wants to add to the website
     * @return the new game as a GamesEntity
     */
    public GamesEntity getChangedGame() { return changedGame; }

    /**
     * Returns all the possible conditions
     * @return an array of the possible conditions
     */
    public Condition[] getConditions() {
        return Condition.values();
    }

    /**
     * Returns all the games in the website, to show in the page's table
     * @return a list of all the games in the website
     */
    public List<GamesEntity> getGames() {
        // Runs the query getAllGames in GamesEntity
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getAllGames = entityManager.createNamedQuery("getAllGames", GamesEntity.class);
        return getAllGames.getResultList();
    }

    /**
     * Finds if there are any selected games
     * @return true if there's a selected game and false otherwise
     */
    public boolean hasSelectedGames() {
        return selectedGames != null && !selectedGames.isEmpty();
    }

    /**
     * Created a new GamesEntity for the game to be created in the "Add game" dialog
     */
    public void openAddGameDialog() {
        newGame = new GamesEntity();
    }

    /**
     * Created a new GamesEntity for the game to be created in the "Add game" dialog
     */
    public void openEditGameDialog(GamesEntity game) { changedGame = game; }

    /**
     * Adds the created game to the database and to the page's table
     */
    public void saveGame() {
        // Adds the new game to the database
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newGame);
        transaction.commit();

        PrimeFaces.current().executeScript("PF('newGameDialog').hide()"); // Hides the "Add game" dialog
        // Updates the relevant components in the page
        PrimeFaces.current().ajax().update(":form:messages", ":form:games-data-table");
        addNotification("Game Added"); // Notifies the admin that the game was added
    }

    public void editGame(int gameID) {
        // Adds the new game to the database
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        GamesEntity game = entityManager.find(GamesEntity.class, gameID);
        game.setName(changedGame.getName());
        game.setPublisher(changedGame.getPublisher());
        game.setDeveloper(changedGame.getDeveloper());
        game.setReleaseDate(changedGame.getReleaseDate());
        game.setPrice(changedGame.getPrice());
        game.setStock(changedGame.getStock());
        game.setCondition(changedGame.getCondition());
        entityManager.persist(game);
        transaction.commit();

        PrimeFaces.current().executeScript("PF('editGameDialog').hide()"); // Hides the "Add game" dialog
        // Updates the relevant components in the page
        PrimeFaces.current().ajax().update(":form:messages", ":form:games-data-table");
        addNotification("Game Edited"); // Notifies the admin that the game was added
    }

    /**
     * Returns a string message that holds the number of games
     * selected (if some games were selected)
     * @return the string message for the selected games or "Delete" if there are no selected games
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedGames()) {
            int size = selectedGames.size();
            return size > 1 ? size + " games selected" : "1 game selected";
        }
        return "Delete";
    }

    /**
     * Deletes the selected games
     */
    public void deleteSelectedGames() {
        if (!hasSelectedGames()) return; // If there are no selected games, there's nothing to do

        int removedGamesAmount = selectedGames.size();
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // For every selected game, we remove it's pairs in the category_members table and the game itself
        for (GamesEntity selectedGame : selectedGames) {
            deleteAllCategoryMembersOfGame(entityManager, selectedGame.getId());
            if (entityManager.contains(selectedGame))
                entityManager.remove(selectedGame);
        }
        transaction.commit();

        selectedGames = null; // There's no list of selected games now - they were all deleted

        // Notifies the admin that the games were deleted
        addNotification("Deleted " + removedGamesAmount + " game" + (removedGamesAmount == 1 ? "" : "s") + ".");

        // Updates the relevant components in the page
        PrimeFaces.current().ajax().update("form:messages", "form:games-data-table");
    }

    /**
     *
     * @param game the game that the dialog is opened for
     */
    public void handleGameImageDialogOpening(GamesEntity game) {
        changingImageGame = game;
    }

    /**
     * Handles uploading of the game image
     *
     * @param event the file upload event
     */
    public void handleImageUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        boolean isFileValid = file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null;
        if (!isFileValid) return;
        try {
            changingImageGame.setImage(file.getInputStream());
            // Show success message
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception exception) {
            // Show failure message
            System.err.println(exception.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure", "Could not upload image " + file.getFileName());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /*
     * Deletes all the pair with the given gameId
     * Parameters: entityManager - will delete all the matching pairs
     *             gameId - the id of the game we delete all of it's pairs
     */
    private void deleteAllCategoryMembersOfGame(EntityManager entityManager, int gameId) {
        // Runs the query findCategoryMembersByGameId in CategoryMembersEntity with the given game id
        TypedQuery<CategoryMembersEntity> findCategoryMembersByGameId =
                entityManager.createNamedQuery("findCategoryMembersByGameId", CategoryMembersEntity.class);
        findCategoryMembersByGameId.setParameter("gameId", gameId);

        // For every pair with the game's id, we delete the pair
        for (CategoryMembersEntity categoryMembersEntity : findCategoryMembersByGameId.getResultList()) {
            if (entityManager.contains(categoryMembersEntity))
                entityManager.remove(categoryMembersEntity);
        }
    }

    /*
     * Shows a notification on the screen
     * Parameters: message - the message of the notification
     */
    private void addNotification(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
}
