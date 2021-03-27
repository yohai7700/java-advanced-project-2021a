package openu.advanced.java_workshop;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Named
@SessionScoped
public class ShoppingCartBean implements Serializable {
    /**
     * Contains a set of game ids of the games that are currently in the shopping cart.
     */
    private final Set<Integer> games = new HashSet<>();

    public ShoppingCartBean(){
        games.add(1);
        games.add(3);
    }

    public Set<Integer> getGames() {
        return games;
    }
}
