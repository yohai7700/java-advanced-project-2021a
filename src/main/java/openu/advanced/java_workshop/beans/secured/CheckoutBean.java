package openu.advanced.java_workshop.beans.secured;

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
        for(GamesEntity game : shoppingCartBean.getGames()){
            games.add(game);
        }
        return games;
    }

    public void checkout() {
        double balance = loginBean.getUser().getBalance();
        double price = shoppingCartBean.getTotal();
        double balanceAfterPurchase = balance - price;
        if (balanceAfterPurchase < 0) {
            String errorMsg = "Can't make the purchase - you dont have enough credit.";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", errorMsg);
            PrimeFaces.current().dialog().showMessageDynamic(message);

        }
        else{
            int purchaseID = addPurchaseToDB();
            addPurchaseGamesToDB(purchaseID);
            updateBalance(balanceAfterPurchase);
        }
    }

    private int addPurchaseToDB() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        PurchasesEntity newPurchase = new PurchasesEntity();
        newPurchase.setUsername(loginBean.getUsername());
        newPurchase.setDate(getCurrentDate());
        entityManager.persist(newPurchase);


        transaction.commit();
        return newPurchase.getId();
    }

    private void addPurchaseGamesToDB(int purchaseID) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        for (GamesEntity game : getGames()){
            transaction.begin();
            PurchasesGamesEntity purchasesGamesEntity = new PurchasesGamesEntity();
            purchasesGamesEntity.setGameId(game.getId());
            purchasesGamesEntity.setPurchaseId(purchaseID);
            entityManager.persist(purchasesGamesEntity);
            transaction.commit();
        }
    }

    private boolean updateBalance(double newBalance) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        UsersEntity user = entityManager.find(UsersEntity.class, loginBean.getUser().getUsername());
        if (user != null) {
            user.setBalance(newBalance);
            transaction.commit();
            return true;
        }
        return false;
    }

    private java.sql.Timestamp getCurrentDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return new java.sql.Timestamp(c.getTimeInMillis());
    }
}
