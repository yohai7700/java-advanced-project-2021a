package openu.advanced.java_workshop.model;

import javax.persistence.*;

@Entity
@Table(name = "coupons", schema = "public", catalog = "workshop")
@NamedQueries(
        @NamedQuery(name = "findAllCoupons", query = "SELECT coupon FROM CouponsEntity coupon")
)
public class CouponsEntity {
    private String code;
    private double value;
    private boolean isUsed;

    @Id
    @Column(name = "code", nullable = false, length = -1)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "value", nullable = false, precision = 0)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Basic
    @Column(name = "is_used", nullable = false)
    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponsEntity that = (CouponsEntity) o;

        if (Double.compare(that.value, value) != 0) return false;
        if (isUsed != that.isUsed) return false;
        return code != null ? code.equals(that.code) : that.code == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = code != null ? code.hashCode() : 0;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isUsed ? 1 : 0);
        return result;
    }

    public void remove(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("workshopPU");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(this) ? this : entityManager.merge(this));
        entityManager.getTransaction().commit();
    }
}
