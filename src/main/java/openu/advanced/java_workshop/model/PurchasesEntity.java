package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchases", schema = "public", catalog = "workshop")
@NamedQueries({
        @NamedQuery(
                name = "findAllPurchasesByUsername",
                query = "SELECT purchase " +
                        "FROM PurchasesEntity purchase " +
                        "WHERE purchase.username = :username"
        ),
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

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "address", nullable = true, length = -1)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasesEntity that = (PurchasesEntity) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "username", nullable = false, length = -1)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
