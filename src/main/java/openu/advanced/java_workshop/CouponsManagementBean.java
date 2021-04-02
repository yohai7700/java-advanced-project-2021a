package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.CouponsEntity;
import openu.advanced.java_workshop.model.UsersSessionsEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Named
@RequestScoped
public class CouponsManagementBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    private final CouponsEntity newCoupon = new CouponsEntity();

    public CouponsEntity getNewCoupon() {
        return newCoupon;
    }

    public List<CouponsEntity> getCoupons() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<CouponsEntity> findAllCoupons = entityManager.createNamedQuery("findAllCoupons", CouponsEntity.class);
        return findAllCoupons.getResultList();
    }

    private static String generateNewCouponCode() {
        SecureRandom secureRandom = new SecureRandom(); //threadsafe
        Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private void addCouponToDB(String couponCode) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        newCoupon.setCode(couponCode);
        System.out.println(newCoupon.getValue());
        entityManager.persist(newCoupon);
        entityManager.getTransaction().commit();
    }

    public void addCoupon() {
        String coupon = generateNewCouponCode();
        this.addCouponToDB(coupon);
    }

    public void deleteCoupon(String couponCode) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        CouponsEntity coupon = entityManager.find(CouponsEntity.class, couponCode);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (coupon != null) {
            entityManager.remove(entityManager.contains(coupon) ? coupon :
                    entityManager.merge(coupon));
        }
        transaction.commit();
    }
}
