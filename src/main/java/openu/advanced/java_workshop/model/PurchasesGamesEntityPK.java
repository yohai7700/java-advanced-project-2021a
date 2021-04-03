package openu.advanced.java_workshop.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class PurchasesGamesEntityPK implements Serializable {
    private int purchaseId;
    private int gameId;

    @Column(name = "purchase_id", nullable = false)
    @Id
    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Column(name = "game_id", nullable = false)
    @Id
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasesGamesEntityPK that = (PurchasesGamesEntityPK) o;

        if (purchaseId != that.purchaseId) return false;
        if (gameId != that.gameId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = purchaseId;
        result = 31 * result + gameId;
        return result;
    }
}
