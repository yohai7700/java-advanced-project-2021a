package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.Condition;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
public class AddGameFormBean implements Serializable {
    private Date releaseDate;
    private float price;
    private int stock;
    private Condition condition;

    public Condition[] getConditions(){
        return Condition.values();
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


}
