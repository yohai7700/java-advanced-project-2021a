package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.Condition;
import openu.advanced.java_workshop.model.Game;

import javax.faces.bean.ManagedBean;
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

    public String save() throws SQLException, NamingException {
        try (Connection connection = WorkshopDatabase.getConnection()){
            PreparedStatement addEntry = connection.prepareStatement(
                    "INSERT INTO games" +
                    "(name, publisher, developer, release_date, price, stock, condition)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            java.sql.Date sqlReleaseDate = new java.sql.Date(getReleaseDate().getTime());
            addEntry.setString(1, getName());
            addEntry.setString(2, getPublisher());
            addEntry.setString(3, getDeveloper());
            addEntry.setDate(4, sqlReleaseDate);
            addEntry.setFloat(5, getPrice());
            addEntry.setInt(6, getStock());
            addEntry.setString(7, getCondition().name());
            addEntry.executeUpdate();
            return "games-table";
        }
    }
}
