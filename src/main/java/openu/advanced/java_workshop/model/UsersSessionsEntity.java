package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entity class for the users_sessions table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "users_sessions", schema = "public", catalog = "workshop")
public class UsersSessionsEntity {
    private String username;
    private String token;
    private java.sql.Timestamp expirationDate;

    /**
     * This constructor receives values for all the attributes
     * @param username the name of the user that runs the session
     * @param token the token of the session
     * @param expirationDate the expiration date of the token of the session
     */
    public UsersSessionsEntity(String username, String token, java.sql.Timestamp expirationDate) {
        this.username = username;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    /**
     * An empty constructor
     */
    public UsersSessionsEntity(){ }


    /**
     * Gives other classes access to the username attribute
     * @return the name of the user who's running the session
     */
    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * Gives other classes the ability to modify the username attribute
     * @param username the new name of the user who's running the session
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gives other classes access to the token attribute
     * @return the token of the session
     */
    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    /**
     * Gives other classes the ability to modify the token attribute
     * @param token the new token of the session
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gives other classes access to the expirationDate attribute
     * @return the expiration date of the token of the session
     */
    @Basic
    @Column(name = "expiration_date")
    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    /**
     * Gives other classes the ability to modify the expirationDate attribute
     * @param expirationDate the new expiration date of the token of the session
     */
    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Checks if this object is equal to another object (if all of the attributes are equal)
     * @param o the other object we compare this object to
     * @return true if the objects are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // If the objects are the same (also null) we return true

        // If o isn't an instance of this class, the objects aren't equal
        if (o == null || o instanceof UsersSessionsEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        UsersSessionsEntity that = (UsersSessionsEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return Objects.equals(username, that.username) && Objects.equals(token, that.token) &&
                Objects.equals(expirationDate, that.expirationDate);
    }

    /**
     * Creates an hash code to identify each session, using its attributes' values
     * @return the hash code for the session
     */
    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
