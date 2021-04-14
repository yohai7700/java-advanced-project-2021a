package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.GamesEntity;
import openu.advanced.java_workshop.model.PurchasesEntity;
import openu.advanced.java_workshop.model.PurchasesGamesEntity;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Exception in case the user is not found
 */
class UserNotFoundException extends Exception {
}

/**
 * Bean class for the checkout.xhtml. Manages the logics of the checkout page
 */
@Named
@RequestScoped
public class CheckoutBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    String address;

    @Inject
    ShoppingCartBean shoppingCartBean;

    /**
     *
     * Returns the current date as an sql timestamp
     * @return the timestamp of the current date
     */
    private static java.sql.Timestamp getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return new java.sql.Timestamp(c.getTimeInMillis());
    }

    /**
     * Gives other classes access to the address attribute
     * @return the address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gives other classes the ability to modify the address attribute
     * @param address the new address of the category
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the games in the shopping cart to show in the checkout page
     * @return the list of the games in the shopping cart
     */
    public List<GamesEntity> getGames() {
        return shoppingCartBean.getGames();
    }

    /**
     * Performs the function of the checkout
     * @throws UserNotFoundException in case the user isn't found in the database
     * @throws IOException caused by the redirect function
     */
    public void checkout() throws UserNotFoundException, IOException {
        double balance = getUser().getBalance();
        double price = shoppingCartBean.getTotal();
        double balanceAfterPurchase = balance - price;

        /* If there isn't enough money in the user's account to buy all the items in the shopping cart
        we show an error message and stop the checkout process */
        if (balanceAfterPurchase < 0) {
            showMessageOnScreen("Can't make the purchase - you dont have enough credit.", true);
        } else { // There's enough money in the in the user's account to buy all the games in the shopping cart
            /* We add the purchase to the purchases table, add the games to the purchases_games table
            and update the balance to the balance after the purchase */
            EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            int purchaseID = addPurchaseToDB(entityManager);
            addPurchaseGamesToDB(entityManager, purchaseID);
            updateBalance(entityManager, balanceAfterPurchase);
            transaction.commit();
            shoppingCartBean.clear(); // Clearing the shopping cart

            // Redirecting to the home page
            FacesContext.getCurrentInstance().getExternalContext().redirect("/public-pages/index.xhtml");
        }
    }

    /**
     * Shows a String message on the screen
     * @param msg the message to show on the screen
     * @param isErrorMsg a flag to determine if the message is an error message
     */
    private void showMessageOnScreen(String msg, boolean isErrorMsg) {
        FacesMessage message;
        if (isErrorMsg) { // Creating an error message or an info message according to the value of isErrorMsg
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", msg);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Info", msg);
        }
        // Shows the message on the screen
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Adds a purchase to the database
     * @param entityManager used to add the purchase to the database
     * @return the id of the purchase that was added to the database
     */
    private int addPurchaseToDB(EntityManager entityManager) {
        PurchasesEntity newPurchase = new PurchasesEntity();
        UsersEntity user = getUser();
        newPurchase.setUsername(user.getUsername()); // Sets the attributes of the purchase
        newPurchase.setDate(getCurrentDate());
        String purchaseAddress = address != null ? address : user.getAddress();
        newPurchase.setAddress(purchaseAddress);
        entityManager.persist(newPurchase);
        return newPurchase.getId();
    }

    /**
     * Adds all the games in the shopping cart to the new purchase in the database
     * @param entityManager used to add the games to the database
     * @param purchaseID the id of the purchase we add the the games to
     */
    private void addPurchaseGamesToDB(EntityManager entityManager, int purchaseID) {
        // For each game, we create a pair of the game id and the purchase id and add it to the purchases_games table
        for (GamesEntity game : getGames()) {
            PurchasesGamesEntity purchasesGamesEntity = new PurchasesGamesEntity();
            purchasesGamesEntity.setGameId(game.getId());
            purchasesGamesEntity.setPurchaseId(purchaseID);
            entityManager.persist(purchasesGamesEntity);
        }
    }

    private void decreaseStockInDB(int gameID, EntityManager entityManager) {
        GamesEntity game = entityManager.find(GamesEntity.class, gameID);
        if (game != null) {
            game.setStock(game.getStock()-1);
            entityManager.persist(game);
        }
    }


    /**
     * Updates the balance of the current user in the database
     * @param entityManager used to update the balance in the database
     * @param newBalance the new balance for the user
     * @throws UserNotFoundException in case the user isn't found
     */
    private void updateBalance(EntityManager entityManager, double newBalance) throws UserNotFoundException {
        // Gets the user that runs this session
        String username = SessionUtils.getUserName();
        UsersEntity user = entityManager.find(UsersEntity.class, username);
        // If there's no user that runs this session, we throw the exception
        if (user == null)
            throw new UserNotFoundException();
        user.setBalance(newBalance); // Otherwise, we set the new balance of the user
    }

    /**
     * Returns the user who's currently in the checkout page
     * @return the entity of the user
     */
    public UsersEntity getUser() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        String username = SessionUtils.getUserName();
        return entityManager.find(UsersEntity.class, username);
    }

    /**
     * Calculates the total price of the games in the shopping cart
     * @return the total price of the cart
     */
    public double getTotalPrice() {
        return shoppingCartBean.getTotal();
    }
}
