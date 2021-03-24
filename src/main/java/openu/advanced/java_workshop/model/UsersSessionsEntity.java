package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;




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
    public java.sql.Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(java.sql.Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersSessionsEntity that = (UsersSessionsEntity) o;
        return Objects.equals(username, that.username) && Objects.equals(token, that.token) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token, expirationDate);
    }
}
