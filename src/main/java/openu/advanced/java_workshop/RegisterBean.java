package openu.advanced.java_workshop;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
@SessionScoped
public class RegisterBean implements Serializable {

    private String username, email, password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String Register(){
        try (Connection connection = WorkshopDatabase.getConnection()){
            PreparedStatement addEntry = connection.prepareStatement(
                    "INSERT INTO users" +
                            "(username, password, address)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            addEntry.setString(1, getUsername());
            addEntry.setString(2, getPassword());
            addEntry.setString(3, getEmail());
            addEntry.executeUpdate();
        } catch (SQLException| NamingException throwables) {
            throwables.printStackTrace();
        }
        return "index";
    }
}
