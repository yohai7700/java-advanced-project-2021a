package openu.advanced.java_workshop.model;

import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable {
    int id;
    String name;
    String publisher;
    String developer;
    Date releaseDate;
    float price;
    int stock;
    Condition condition;

    public Game(){
        this.id = 123;
        this.name = "Day of the Tentacle";
        this.publisher = "Lucas Arts";
        this.developer = "Double Fine";
        this.releaseDate = new Date("25/06/1993");
        this.price = 35.5f;
        this.stock = 32;
        this.condition = Condition.BRAND_NEW;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
