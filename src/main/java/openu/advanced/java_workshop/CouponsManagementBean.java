package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.CouponsEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CouponsManagementBean implements Serializable {
    public List<CouponsEntity> getCoupons(){
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<CouponsEntity> findAllCoupons = entityManager.createNamedQuery("findAllCoupons", CouponsEntity.class);
        return findAllCoupons.getResultList();
    }
}