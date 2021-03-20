package openu.advanced.java_workshop.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CategoryMembersEntityPK implements Serializable {
    private int gameId;
    private int categoryId;

    @Column(name = "game_id", nullable = false)
    @Id
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Column(name = "category_id", nullable = false)
    @Id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryMembersEntityPK that = (CategoryMembersEntityPK) o;

        if (gameId != that.gameId) return false;
        if (categoryId != that.categoryId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + categoryId;
        return result;
    }
}
