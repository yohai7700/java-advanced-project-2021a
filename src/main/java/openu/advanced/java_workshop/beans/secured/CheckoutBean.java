package openu.advanced.java_workshop.beans.secured;

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

class UserNotFoundException extends Exception {
}

@Named
@RequestScoped
public class CheckoutBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    String address;

    @Inject
    ShoppingCartBean shoppingCartBean;

    private static java.sql.Timestamp getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return new java.sql.Timestamp(c.getTimeInMillis());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<GamesEntity> getGames() {
        return shoppingCartBean.getGames();
    }

    public void checkout() throws UserNotFoundException, IOException {
        double balance = getUser().getBalance();
        double price = shoppingCartBean.getTotal();
        double balanceAfterPurchase = balance - price;
        if (balanceAfterPurchase < 0) {
            showMessageOnScreen("Can't make the purchase - you dont have enough credit.", true);
        } else {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            int purchaseID = addPurchaseToDB(entityManager);
            addPurchaseGamesToDB(entityManager, purchaseID);
            updateBalance(entityManager, balanceAfterPurchase);
            transaction.commit();
            shoppingCartBean.clear();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/secured/index.xhtml");
        }
    }

    private void showMessageOnScreen(String msg, boolean isErrorMsg) {
        FacesMessage message;
        if (isErrorMsg) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", msg);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Info", msg);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private int addPurchaseToDB(EntityManager entityManager) {
        PurchasesEntity newPurchase = new PurchasesEntity();
        UsersEntity user = getUser();
        newPurchase.setUsername(user.getUsername());
        newPurchase.setDate(getCurrentDate());
        String purchaseAddress = address != null ? address : user.getAddress();
        newPurchase.setAddress(purchaseAddress);
        entityManager.persist(newPurchase);
        return newPurchase.getId();
    }

    private void addPurchaseGamesToDB(EntityManager entityManager, int purchaseID) {

        for (GamesEntity game : getGames()) {
            PurchasesGamesEntity purchasesGamesEntity = new PurchasesGamesEntity();
            purchasesGamesEntity.setGameId(game.getId());
            purchasesGamesEntity.setPurchaseId(purchaseID);
            entityManager.persist(purchasesGamesEntity);
        }
    }

    private void updateBalance(EntityManager entityManager, double newBalance) throws UserNotFoundException {
        String username = SessionUtils.getUserName();
        UsersEntity user = entityManager.find(UsersEntity.class, username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        user.setBalance(newBalance);
    }

    public UsersEntity getUser() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String username = SessionUtils.getUserName();
        return entityManager.find(UsersEntity.class, username);
    }

    public double getTotalPrice() {
        List<GamesEntity> games = getGames();
        double totalPrice = 0;
        for (GamesEntity game : games) {
            totalPrice += game.getPrice();
        }
        return totalPrice;
    }
}
