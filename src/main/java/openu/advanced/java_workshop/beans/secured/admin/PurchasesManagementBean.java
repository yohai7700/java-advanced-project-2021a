package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.PurchasesEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class PurchasesManagementBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    public List<PurchasesEntity> getPurchases(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<PurchasesEntity> findAllPurchases = entityManager.createNamedQuery("findAllPurchases", PurchasesEntity.class);
        return findAllPurchases.getResultList();
    }
}
