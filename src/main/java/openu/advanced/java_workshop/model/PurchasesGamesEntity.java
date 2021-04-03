package openu.advanced.java_workshop.model;

import javax.persistence.*;

@Entity
@Table(name = "purchases_games", schema = "public", catalog = "workshop")
@IdClass(PurchasesGamesEntityPK.class)
public class PurchasesGamesEntity {
    private int purchaseId;
    private int gameId;

    @Id
    @Column(name = "purchase_id", nullable = false)
    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Id
    @Column(name = "game_id", nullable = false)
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

        PurchasesGamesEntity that = (PurchasesGamesEntity) o;

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
