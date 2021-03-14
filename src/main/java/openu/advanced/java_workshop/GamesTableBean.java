package openu.advanced.java_workshop;

import openu.advanced.java_workshop.model.Condition;
import openu.advanced.java_workshop.model.Game;

import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
public class GamesTableBean implements Serializable {
    private List<Game> selectedGames;
    public boolean selected;

    public void openNewGameForm(){
        this.selected = true;
    }

    public boolean getIsNewGameForm(){
        return this.selected;
    }

    public void setIsNewGameForm(boolean value){
        this.selected = value;
    }

    private List<Game> convertResultSetToList(ResultSet resultSet) throws SQLException {
        List<Game> games = new ArrayList<>();
        while(resultSet.next()){
            Game game = new Game();
            game.setId(resultSet.getInt("id"));
            game.setName(resultSet.getString("name"));
            game.setPublisher(resultSet.getString("publisher"));
            game.setDeveloper(resultSet.getString("developer"));
            game.setReleaseDate(resultSet.getDate("release_date"));
            game.setStock(resultSet.getInt("stock"));
            game.setPrice(resultSet.getFloat("price"));
            game.setCondition(Condition.valueOf(resultSet.getString("condition")));
            games.add(game);
        }
        return games;
    }

    public List<Game> getGames() throws SQLException, NamingException {
        try (Connection connection = WorkshopDatabase.getConnection()) {
            PreparedStatement getGames = connection.prepareStatement("SELECT * FROM games");
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(getGames.executeQuery());
            return convertResultSetToList(rowSet);
        }
    }
}
