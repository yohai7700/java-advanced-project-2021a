package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users_sessions", schema = "public", catalog = "workshop")
public class UsersSessionsEntity {
    private String username;
    private String token;
    private java.sql.Timestamp expirationDate;

    public UsersSessionsEntity(String username, String token, java.sql.Timestamp expirationDate) {
        this.username = username;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public UsersSessionsEntity(){

    }


    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "expiration_date")
    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersSessionsEntity that = (UsersSessionsEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
