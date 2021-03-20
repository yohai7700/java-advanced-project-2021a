package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "games", schema = "public", catalog = "workshop")
public class GamesEntity {
    private int id;
    private String name;
    private String publisher;
    private String developer;
    private Date releaseDate;
    private double price;
    private int stock;
    private Condition condition;

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

    @Basic
    @Column(name = "publisher", nullable = false, length = 20)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "developer", nullable = false, length = 20)
    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    @Basic
    @Column(name = "release_date", nullable = false)
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "stock", nullable = false)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "condition", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesEntity that = (GamesEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (stock != that.stock) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (developer != null ? !developer.equals(that.developer) : that.developer != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (developer != null ? developer.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + stock;
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        return result;
    }
}
