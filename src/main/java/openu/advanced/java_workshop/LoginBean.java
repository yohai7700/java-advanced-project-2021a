package openu.advanced.java_workshop;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String password, username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String loginProject(){
        try (Connection connection = WorkshopDatabase.getConnection()){
            PreparedStatement addEntry = connection.prepareStatement(
                    "SELECT username FROM users WHERE username= ? and password= ? "
            );
            addEntry.setString(1, getUsername());
            addEntry.setString(2, getPassword());
            ResultSet rs = addEntry.executeQuery();

            if (rs.next()) // found
            {
                String token = AuthenticationToken.createSessionTokenForUser(rs.getString("username"));
                System.out.println(rs.getString("username"));
                return "index";
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Login Error",
                        "Wrong username/password."));
            }

        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }


}
