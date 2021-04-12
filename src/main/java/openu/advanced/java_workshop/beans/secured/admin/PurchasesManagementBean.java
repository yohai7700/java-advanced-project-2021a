package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.PurchasesEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * This bean handles management of the purchases-management.xhtml page
 */

@Named
@RequestScoped
public class PurchasesManagementBean implements Serializable {

    /**
     * Finds all of the purchases in the website
     * @return a list of the purchases as PurchasesEntity
     */
    public List<PurchasesEntity> getPurchases() {
        // Runs the query findAllPurchases in PurchasesEntity
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<PurchasesEntity> findAllPurchases =
                entityManager.createNamedQuery("findAllPurchases", PurchasesEntity.class);
        return findAllPurchases.getResultList();
    }
}
