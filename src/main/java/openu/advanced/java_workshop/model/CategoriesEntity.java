package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categories", schema = "public", catalog = "workshop")
@NamedQueries({
        @NamedQuery(name = "findAllCategories", query = "SELECT category FROM CategoriesEntity category"),
        @NamedQuery(name = "findCategoryById", query = "SELECT category FROM CategoriesEntity category WHERE category.id = :id"),
        @NamedQuery(name = "findGamesByCategoryId", query =
                "SELECT game FROM GamesEntity game WHERE " +
                        "EXISTS (SELECT categoryMember FROM CategoryMembersEntity categoryMember " +
                        "WHERE categoryMember.gameId=game.id AND categoryMember.categoryId=:categoryId)")
})
public class CategoriesEntity implements Serializable {
    private int id;
    private String name;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriesEntity that = (CategoriesEntity) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " Category";
    }
}
