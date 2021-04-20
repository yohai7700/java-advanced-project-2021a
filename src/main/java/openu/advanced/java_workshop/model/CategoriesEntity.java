package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class for the categories table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "categories", schema = "public", catalog = "workshop")
@NamedQueries({
        // Returns all of the possible categories in the table
        @NamedQuery(name = "findAllCategories", query = "SELECT category FROM CategoriesEntity category"),

        // Finds the category with the given category id
        @NamedQuery(name = "findCategoryById", query = "SELECT category FROM CategoriesEntity category" +
                " WHERE category.id = :id"),

        // Returns all the games in the category with the given category id
        @NamedQuery(name = "findGamesByCategoryId", query =
                "SELECT game FROM GamesEntity game WHERE " +
                        "EXISTS (SELECT categoryMember FROM CategoryMembersEntity categoryMember " +
                        "WHERE categoryMember.gameId=game.id AND categoryMember.categoryId=:categoryId)")
})
public class CategoriesEntity implements Serializable {
    private int id;
    private String name;

    /**
     * Gives other classes access to the id attribute
     * @return the id of the category
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Gives other classes the ability to modify the id attribute
     * @param id the new id of the category
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gives other classes access to the name attribute
     * @return the name of the category
     */
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    /**
     * Gives other classes the ability to modify the name attribute
     * @param name the new name of the category
     */
    public void setName(String name) {
        this.name = name;
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
        if (o == null || o instanceof CategoriesEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        CategoriesEntity that = (CategoriesEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return id == that.id && Objects.equals(name, that.name);
    }

    /**
     * Creates an hash code to identify each category, using the id and the hash code of the name
     * @return the hash code for the category
     */
    @Override
    public int hashCode() {
        return 31 * id + (name != null ? name.hashCode() : 0);
    }

    /**
     * Represents the Category - The name of the category + Category
     * @return the string representation of the category
     */
    @Override
    public String toString() {
        return name + " Category";
    }
}
