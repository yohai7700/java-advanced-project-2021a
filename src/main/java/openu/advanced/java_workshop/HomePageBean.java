package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class HomePageBean {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("workshopPU");

    public List<CategoriesEntity> getCategories() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<CategoriesEntity> getCategories = entityManager.createNamedQuery("findAllCategories", CategoriesEntity.class);
        return getCategories.getResultList();
    }

    public List<GamesEntity> getGames(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> getCategoryById = entityManager.createNamedQuery("findGamesByCategoryId", GamesEntity.class);
        getCategoryById.setParameter("categoryId", id);
        return getCategoryById.getResultList();
    }
}
