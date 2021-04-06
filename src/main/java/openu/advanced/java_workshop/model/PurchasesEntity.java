package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

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
    private String username;
    private Timestamp date;
    private String address;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "address")
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
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(date, that.date) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, date, address);
    }
}
