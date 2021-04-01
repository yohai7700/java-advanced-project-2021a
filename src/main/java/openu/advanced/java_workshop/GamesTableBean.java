package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.Condition;
import openu.advanced.java_workshop.model.Game;
import openu.advanced.java_workshop.model.GamesEntity;
import org.hibernate.jdbc.Work;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.transaction.Transaction;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class GamesTableBean implements Serializable {
    private GamesEntity newGame;

    public List<GamesEntity> getGames() {
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        TypedQuery<GamesEntity> getAllGames = entityManager.createNamedQuery("getAllGames", GamesEntity.class);
        return getAllGames.getResultList();
    }

    public void openAddGameDialog() {
        newGame = new GamesEntity();
    }

    public GamesEntity getNewGame() {
        return newGame;
    }

    public Condition[] getConditions(){
        return Condition.values();
    }

    public void saveGame(){
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newGame);
        transaction.commit();
    }
}
