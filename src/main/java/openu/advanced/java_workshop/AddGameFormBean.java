package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.Condition;
import openu.advanced.java_workshop.model.Game;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ManagedBean
public class AddGameFormBean  extends Game implements Serializable {
    public Condition[] getConditions(){
        return Condition.values();
    }

    public void save() throws SQLException, NamingException {
        try (Connection connection = WorkshopDatabase.getConnection()){
            PreparedStatement addEntry = connection.prepareStatement(
                    "INSERT INTO games" +
                    "(name, publisher, developer, release_date, price, stock, condition)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            System.out.println(getReleaseDate());
            java.sql.Date sqlReleaseDate = new java.sql.Date(getReleaseDate().getTime());
            addEntry.setString(1, getName());
            addEntry.setString(2, getPublisher());
            addEntry.setString(3, getDeveloper());
            addEntry.setDate(4, sqlReleaseDate);
            addEntry.setFloat(5, getPrice());
            addEntry.setInt(6, getStock());
            addEntry.setString(7, getCondition().name());
            addEntry.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Game Added"));
            PrimeFaces.current().executeScript("PF('addGameDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:games-table");
        }
    }
}
