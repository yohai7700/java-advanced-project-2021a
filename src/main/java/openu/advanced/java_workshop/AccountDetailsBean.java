package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.PurchasesEntity;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Named
@RequestScoped
public class AccountDetailsBean {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();

    public UsersEntity getUser(){
        return SessionUtils.getUser();
    }

    public List<PurchasesEntity> getPurchases(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<PurchasesEntity> findAllPurchasesByUsername = entityManager.createNamedQuery("findAllPurchasesByUsername", PurchasesEntity.class);
        findAllPurchasesByUsername.setParameter("username", getUser().getUsername());
        return findAllPurchasesByUsername.getResultList();
    }
}
