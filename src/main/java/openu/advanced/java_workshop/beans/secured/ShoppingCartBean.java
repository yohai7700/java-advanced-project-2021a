package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ShoppingCartBean implements Serializable {

    private final List<GamesEntity> games = new ArrayList<>();

    public List<GamesEntity> getGames() { return games; }

    public void addGame(GamesEntity game) { games.add(game); }

    public void removeGame(GamesEntity game) { games.remove(game); }

    public void isEmpty() throws IOException {
        if (games.isEmpty()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/secured/index.xhtml");
        }
    }

    public double getTotal() {
        double sum = 0;
        for(GamesEntity game : games) sum += game.getPrice();
        return sum;
    }
}
