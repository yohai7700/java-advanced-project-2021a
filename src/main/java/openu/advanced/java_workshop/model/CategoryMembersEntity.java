package openu.advanced.java_workshop.model;

import javax.persistence.*;

@Entity
@Table(name = "category_members", schema = "public", catalog = "workshop")
@IdClass(CategoryMembersEntityPK.class)
@NamedQueries(
        @NamedQuery(
                name = "findCategoryMembersByGameId",
                query = "SELECT categoryMember " +
                        "FROM CategoryMembersEntity categoryMember " +
                        "WHERE categoryMember.gameId = :gameId"
        )
)
public class CategoryMembersEntity {
    private int gameId;
    private int categoryId;

    @Id
    @Column(name = "game_id", nullable = false)
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Id
    @Column(name = "category_id", nullable = false)
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

        CategoryMembersEntity that = (CategoryMembersEntity) o;

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
