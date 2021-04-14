package openu.advanced.java_workshop.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Holds the primary keys of the purchases_games table
 */

public class PurchasesGamesEntityPK implements Serializable {
    private int purchaseId;
    private int gameId;

    /**
     * Gives other classes access to the purchaseId attribute
     * @return the id of the purchase in the pair
     */
    @Column(name = "purchase_id", nullable = false)
    @Id
    public int getPurchaseId() {
        return purchaseId;
    }

    /**
     * Gives other classes the ability to modify the purchaseId attribute
     * @param purchaseId the new id of the purchase
     */
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * Gives other classes access to the gameId attribute
     * @return the id of the game in the pair
     */
    @Column(name = "game_id", nullable = false)
    @Id
    public int getGameId() {
        return gameId;
    }

    /**
     * Gives other classes the ability to modify the gameId attribute
     * @param gameId the new id of the game
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
        if (o == null || o instanceof PurchasesGamesEntityPK) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        PurchasesGamesEntityPK that = (PurchasesGamesEntityPK) o;

        // If the attributes of the objects are equal, the objects are equal
        return gameId == that.gameId && purchaseId == that.purchaseId;
    }

    /**
     * Creates an hash code to identify each pair, using gameId and purchaseId
     * @return the hash code for the pair
     */
    @Override
    public int hashCode() {
        return  31 * purchaseId + gameId;
    }
}
