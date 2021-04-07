package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.beans.LoginBean;
import openu.advanced.java_workshop.model.*;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Named
@RequestScoped
public class CheckoutBean implements Serializable {
    @Inject
    ShoppingCartBean shoppingCartBean;
    @Inject
    LoginBean loginBean;

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();

    public List<GamesEntity> getGames() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        List<GamesEntity> games = new ArrayList<>();
        for (GamesEntity game : shoppingCartBean.getGames()) {
            games.add(game);
        }
        return games;
    }

    public void checkout() {
        double balance = SessionUtils.getUser().getBalance();
        double price = shoppingCartBean.getTotal();
        double balanceAfterPurchase = balance - price;
        if (balanceAfterPurchase < 0) {
            String errorMsg = "Can't make the purchase - you dont have enough credit.";
            showMsgOnScreen(errorMsg, true);

        } else {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            int purchaseID = addPurchaseToDB(entityManager);
            addPurchaseGamesToDB(entityManager, purchaseID);
            updateBalance(entityManager, balanceAfterPurchase);
            transaction.commit();
            showMsgOnScreen("purchase made successfully", false);
        }
    }


    private void showMsgOnScreen(String msg, boolean isErrorMsg) {
        FacesMessage message;
        if (isErrorMsg)
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", msg);
        else
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Info", msg);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    private int addPurchaseToDB(EntityManager entityManager) {
        PurchasesEntity newPurchase = new PurchasesEntity();
        newPurchase.setUsername(loginBean.getUsername());
        newPurchase.setDate(getCurrentDate());
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

    private boolean updateBalance(EntityManager entityManager, double newBalance) {
        UsersEntity user = entityManager.find(UsersEntity.class, loginBean.getUser()
                .getUsername());
        if (user != null) {
            user.setBalance(newBalance);
            return true;
        }
        return false;
    }

    private java.sql.Timestamp getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return new java.sql.Timestamp(c.getTimeInMillis());
    }
}
