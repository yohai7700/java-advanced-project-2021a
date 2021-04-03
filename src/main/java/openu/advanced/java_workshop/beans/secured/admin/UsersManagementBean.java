package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

class NotExistingUserException extends Exception {}

@Named
@RequestScoped
public class UsersManagementBean implements Serializable {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();

    public List<UsersEntity> getUsers(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<UsersEntity> findAllUsers = entityManager.createNamedQuery("findAllUsers", UsersEntity.class);
        return findAllUsers.getResultList();
    }

    public void makeUserAdmin(String username) throws NotExistingUserException {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        UsersEntity user = entityManager.find(UsersEntity.class, username);
        if(user == null){
            throw new NotExistingUserException();
        }
        user.setIsAdmin(true);
        transaction.commit();
    }
}
