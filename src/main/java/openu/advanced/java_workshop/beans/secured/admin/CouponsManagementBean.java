package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.authentication.AuthenticationToken;
import openu.advanced.java_workshop.model.CouponsEntity;
import openu.advanced.java_workshop.model.UsersSessionsEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * Creates and manages coupons for the website
 */

@Named
@RequestScoped
public class CouponsManagementBean implements Serializable {

    private final CouponsEntity newCoupon = new CouponsEntity();

    /**
     * Creates an empty coupon
     * @return the created coupon
     */
    public CouponsEntity getNewCoupon() {
        return newCoupon;
    }

    /**
     * Returns a list of all the coupons
     * @return the list of the coupons
     */
    public List<CouponsEntity> getCoupons() {
        // Calling the query findAllCoupons in CouponsEntity
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<CouponsEntity> findAllCoupons =
                entityManager.createNamedQuery("findAllCoupons", CouponsEntity.class);
        return findAllCoupons.getResultList();
    }

    /**
     * Generates a random coupon code
     * @return the generated coupon code
     */
    private static String generateNewCouponCode() {
        SecureRandom secureRandom = new SecureRandom(); // Creates random values
        Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // Encodes from bytes to strings

        byte[] randomBytes = new byte[AuthenticationToken.TOKEN_LEN];
        secureRandom.nextBytes(randomBytes); // Creates random array of bytes
        return base64Encoder.encodeToString(randomBytes); // Encodes the array of bytes to a string
    }

    /**
     * Adds a coupon to the database
     * @param couponCode the code of the coupon to add to the database
     */
    private void addCouponToDB(String couponCode) {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        newCoupon.setCode(couponCode);
        entityManager.persist(newCoupon);
        entityManager.getTransaction().commit();
    }

    /**
     * Generates a coupon and adds it to the database
     */
    public void addCoupon() {
        String coupon = generateNewCouponCode();
        this.addCouponToDB(coupon);
    }

    /**
     * Deletes a coupon from the database
     * @param couponCode the coupon
     */
    public void deleteCoupon(String couponCode) {
        // Finds the coupon with the given code
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        CouponsEntity coupon = entityManager.find(CouponsEntity.class, couponCode);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Deletes the coupon
        if (coupon != null && entityManager.contains(coupon))
            entityManager.remove(coupon);
        transaction.commit();
    }
}
