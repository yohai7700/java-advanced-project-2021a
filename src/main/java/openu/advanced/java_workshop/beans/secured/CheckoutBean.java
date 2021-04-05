package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CheckoutBean implements Serializable {
    @Inject
    ShoppingCartBean shoppingCartBean;

    public List<GamesEntity> getGames(){
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();
        List<GamesEntity> games = new ArrayList<>();
        for(GamesEntity game : shoppingCartBean.getGames()){
            games.add(game);
        }
        return games;
    }
}
