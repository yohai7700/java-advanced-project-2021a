package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entity class for the purchases table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "purchases", schema = "public", catalog = "workshop")
@NamedQueries({
        // Returns all of the purchases of a user with the given username
        @NamedQuery(
                name = "findAllPurchasesByUsername",
                query = "SELECT purchase " +
                        "FROM PurchasesEntity purchase " +
                        "WHERE purchase.username = :username"
        ),

        // Returns all of the purchases
        @NamedQuery(
                name = "findAllPurchases",
                query = "SELECT purchase FROM PurchasesEntity purchase"
        )
})
public class PurchasesEntity {
    private int id;
    private Timestamp date;
    private String address;
    private String username;

    /**
     * Gives other classes access to the id attribute
     * @return the id of the purchase
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Gives other classes the ability to modify the id attribute
     * @param id the new id of the purchase
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gives other classes access to the date attribute
     * @return the date of the purchase
     */
    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    /**
     * Gives other classes the ability to modify the date attribute
     * @param date the new date of the purchase
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * Gives other classes access to the address attribute
     * @return the address of the purchase
     */
    @Basic
    @Column(name = "address", nullable = true, length = -1)
    public String getAddress() {
        return address;
    }

    /**
     * Gives other classes the ability to modify the address attribute
     * @param address the new address of the purchase
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gives other classes access to the username attribute
     * @return the username of the purchase
     */
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (o == null || o instanceof PurchasesEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        PurchasesEntity that = (PurchasesEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return id == that.id && Objects.equals(date, that.date) && Objects.equals(address, that.address);
    }

    /**
     * Creates an hash code to identify each purchase, using the purchase's attributes
     * @return the hash code for the purchase
     */
    @Override
    public int hashCode() {
        int result = 31 * id + (date != null ? date.hashCode() : 0);
        return 31 * result + (address != null ? address.hashCode() : 0);
    }
}
