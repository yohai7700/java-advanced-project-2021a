package openu.advanced.java_workshop.model;

import javax.persistence.*;

/**
 * Entity class for the category_members table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "category_members", schema = "public", catalog = "workshop")
@IdClass(CategoryMembersEntityPK.class)
@NamedQueries(
        // Gets all pairs of games and categories with a particular game id
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
    /**
     * Gives other classes access to the gameId attribute
     * @return the id of the game in the pair
     */
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

    @Id
    @Column(name = "category_id", nullable = false)
    /**
     * Gives other classes access to the categoryId attribute
     * @return the id of the category in the pair
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Gives other classes the ability to modify the categoryId attribute
     * @param categoryId the new id of the game
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
        if (o == null || o instanceof CategoryMembersEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        CategoryMembersEntity that = (CategoryMembersEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return gameId == that.gameId && categoryId == that.categoryId;
    }

    /**
     * Creates an hash code to identify each pair, using gameId and categoryId
     * @return the hash code for the pair
     */
    @Override
    public int hashCode() {
        return 31 * gameId + categoryId;
    }
}
