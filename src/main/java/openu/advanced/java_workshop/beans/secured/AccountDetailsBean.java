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
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Named
@RequestScoped
public class AccountDetailsBean {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();

    private String couponCode;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public UsersEntity getUser() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        return getUser(entityManager);
    }

    private UsersEntity getUser(EntityManager entityManager) {
        String username = SessionUtils.getUserName();
        return entityManager.find(UsersEntity.class, username);
    }

    public List<PurchasesEntity> getPurchases() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<PurchasesEntity> findAllPurchasesByUsername = entityManager.createNamedQuery("findAllPurchasesByUsername", PurchasesEntity.class);
        findAllPurchasesByUsername.setParameter("username", getUser().getUsername());
        return findAllPurchasesByUsername.getResultList();
    }

    private boolean validateCoupon(CouponsEntity coupon) {
        if (coupon == null) {
            addNotification(FacesMessage.SEVERITY_ERROR, "Invalid Coupon", "The coupon was not found.");
            return false;
        }
        if (coupon.isUsed()) {
            addNotification(FacesMessage.SEVERITY_ERROR, "Invalid Coupon", "This coupon was already used.");
            return false;
        }
        return true;
    }

    public void useCoupon() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CouponsEntity coupon = entityManager.find(CouponsEntity.class, couponCode);
        UsersEntity user = getUser(entityManager);

        if(!validateCoupon(coupon)){
            return;
        }

        user.setBalance(user.getBalance() + coupon.getValue());
        coupon.setUsed(true);
        transaction.commit();
        String successMessage = "" + coupon.getValue() + "$ were added to your account's balance.";
        addNotification(FacesMessage.SEVERITY_INFO, "Successfully Used Coupon", successMessage);
    }

    private void addNotification(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
