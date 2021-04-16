package openu.advanced.java_workshop.model;

import openu.advanced.java_workshop.WorkshopDatabase;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity class for the coupons table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "coupons", schema = "public", catalog = "workshop")
@NamedQueries(
        // Returns all of the coupons in the table
        @NamedQuery(name = "findAllCoupons", query = "SELECT coupon FROM CouponsEntity coupon")
)
public class CouponsEntity {
    private String code;
    private double value;
    private boolean isUsed;

    /**
     * Gives other classes access to the coupon attribute
     * @return the code of the coupon
     */
    @Id
    @Column(name = "code", nullable = false, length = -1)
    public String getCode() {
        return code;
    }

    /**
     * Gives other classes the ability to modify the code attribute
     * @param code the new code of the game
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gives other classes access to the value attribute
     * @return the value of the coupon
     */
    @Basic
    @Column(name = "value", nullable = false, precision = 0)
    public double getValue() {
        return value;
    }

    /**
     * Gives other classes the ability to modify the value attribute
     * @param value the new value of the game
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gives other classes access to the isUsed attribute
     * @return true if the coupon was used and false otherwise
     */
    @Basic
    @Column(name = "is_used", nullable = false)
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * Gives other classes the ability to modify the isUsed attribute
     * @param used the new value of the isUsed attribute
     */
    public void setUsed(boolean used) {
        isUsed = used;
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
        if (o == null || o instanceof CouponsEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        CouponsEntity that = (CouponsEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return Double.compare(that.value, value) == 0 && isUsed == that.isUsed && Objects.equals(code, that.code);
    }

    /**
     * Creates an hash code to identify each coupon, using all of it's attributes
     * @return the hash code for the coupon
     */
    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        long temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return 31 * result + (isUsed ? 1 : 0);
    }

    /**
     * Removes the coupon from the coupons table
     */
    public void remove() {
        // Creates an entity manager which will remove this coupon
        EntityManager entityManager = WorkshopDatabase.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        // If the coupon is in the coupons table, we remove it
        if(entityManager.contains(this))
            entityManager.remove(this);
        entityManager.getTransaction().commit(); // Commits the change to the database
    }
}
