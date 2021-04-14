package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.SessionUtils;
import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.CouponsEntity;
import openu.advanced.java_workshop.model.PurchasesEntity;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A bean class that holds and gives access to the details of a user's account
 */

@Named
@RequestScoped
public class AccountDetailsBean {

    private String couponCode;

    /**
     * Gives other files access to the coupon code attribute
     * @return the current coupon code
     */
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    /**
     * Gives other files access to the user attribute
     * @return the current user
     */
    public UsersEntity getUser() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        return getUser(entityManager);
    }

    /*
    Gets the current user with the entity manager
     */
    private UsersEntity getUser(EntityManager entityManager) {
        String username = SessionUtils.getUserName();
        return entityManager.find(UsersEntity.class, username);
    }

    /**
     * Returns all of the purchases of the current user
     * @return a list of the purchases of the current user
     */
    public List<PurchasesEntity> getPurchases() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<PurchasesEntity> findAllPurchasesByUsername =
                entityManager.createNamedQuery("findAllPurchasesByUsername", PurchasesEntity.class);
        findAllPurchasesByUsername.setParameter("username", getUser().getUsername());
        return findAllPurchasesByUsername.getResultList();
    }

    /**
     * Validates the coupon (If there's a coupon and if it wasn't used)
     * @param coupon the coupon we need to validate
     * @return true if the coupon is valid and false otherwise
     */
    private boolean validateCoupon(CouponsEntity coupon) {
        if (coupon == null) {
            addNotification(FacesMessage.SEVERITY_ERROR,
                    "Invalid Coupon", "The coupon was not found.");
            return false;
        }
        if (coupon.isUsed()) {
            addNotification(FacesMessage.SEVERITY_ERROR,
                    "Invalid Coupon", "This coupon was already used.");
            return false;
        }
        return true;
    }

    /**
     * Uses a coupon - adds the value of the coupon to the user's balance
     */
    public void useCoupon() {
        // Creates a transaction to add the coupon's value to the user's balance
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Get's the coupon and the user we'll add the coupon's value to
        CouponsEntity coupon = entityManager.find(CouponsEntity.class, couponCode);
        UsersEntity user = getUser(entityManager);

        // If the coupon is valid, we add it's value to the user's balance and set it as used
        if(validateCoupon(coupon)) {
            user.setBalance(user.getBalance() + coupon.getValue());
            coupon.setUsed(true);
            transaction.commit();
            String successMessage = "" + coupon.getValue() + "$ were added to your account's balance.";

            // Shows notification that the coupon was used successfully
            addNotification(FacesMessage.SEVERITY_INFO, "Successfully Used Coupon", successMessage);
        }
    }

    /*
    Shows a notification on the screen
     */
    private void addNotification(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
