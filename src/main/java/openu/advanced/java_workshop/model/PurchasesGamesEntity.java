package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity class for the purchases_games table in the database
 * Holds pairs of a purchase and a game in the purchase
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "purchases_games", schema = "public", catalog = "workshop")
@IdClass(PurchasesGamesEntityPK.class)
public class PurchasesGamesEntity {
    private int purchaseId;
    private int gameId;

    /**
     * Gives other classes access to the purchaseId attribute
     * @return the id of the purchase in the pair
     */
    @Id
    @Column(name = "purchase_id", nullable = false)
    public int getPurchaseId() {
        return purchaseId;
    }

    /**
     * Gives other classes the ability to modify the purchaseId attribute
     * @param purchaseId the new id of the purchase in the pair
     */
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * Gives other classes access to the gameId attribute
     * @return the id of the game in the pair
     */
    @Id
    @Column(name = "game_id", nullable = false)
    public int getGameId() {
        return gameId;
    }

    /**
     * Gives other classes the ability to modify gameId attribute
     * @param gameId the new id of the game in the pair
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * Checks if this object is equal to another object (if all of the attributes are equal)
     * @param o the other object we compare this object to
     * @return true if the objects are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // If the objects are the same (also null) we return true

        // If o isn't an instance of this class, the objects aren't equal
        if (o == null || o instanceof PurchasesGamesEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        PurchasesGamesEntity that = (PurchasesGamesEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return gameId == that.gameId && purchaseId == that.purchaseId;
    }

    /**
     * Creates an hash code to identify each pair, using the gameId and the purchaseId attributes
     * @return the hash code for the pair
     */
    @Override
    public int hashCode() {
        return 31 * purchaseId + gameId;
    }
}
