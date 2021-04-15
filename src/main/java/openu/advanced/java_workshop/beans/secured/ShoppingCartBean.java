package openu.advanced.java_workshop.beans.secured;

import openu.advanced.java_workshop.model.GamesEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean class for the cart.xhtml. Manages the logics of the shopping cart
 */

@Named
@SessionScoped
public class ShoppingCartBean implements Serializable {

    private final Map<Integer, GamesEntity> games = new HashMap<>();

    /**
     * Returns all of the games in the shopping cart
     * @return a list of the games in the cart
     */
    public List<GamesEntity> getGames() {
        return new ArrayList<>(games.values());
    }

    /**
     *
     * @param gameId the game id to check if is in the shopping cart.
     * @return whether the game is in the shopping cart
     */
    public boolean contains(int gameId) {
        return games.containsKey(gameId);
    }

    /**
     * Removes all the games in the shopping cart
     */
    public void clear(){
        games.clear();
    }

    /**
     * Adds a game to the shopping cart
     * @param game the game the user wants to add to the shopping cart
     */
    public void addGame(GamesEntity game) {
        games.put(game.getId(), game);
    }

    /**
     * Removes the requested game from the cart
     * @param game the game the user wants to remove from the cart
     */
    public void removeGame(GamesEntity game) { games.remove(game.getId()); }

    /**
     * Checks if the shopping cart is empty.
     * If it is, we redirect the user to the home page to buy new games
     * @throws IOException required for the redirect method
     */
    public void isEmpty() throws IOException {
        if (games.isEmpty()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/secured/index.xhtml");
        }
    }

    /**
     * Gets the total price of the shopping cart (the sum of all the games' prices)
     * @return the total price of all of the games in the shopping cart
     */
    public double getTotal() {
        double sum = 0;
        for(GamesEntity game : games.values()) sum += game.getPrice();
        return sum;
    }
}
