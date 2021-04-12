package openu.advanced.java_workshop.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity class for the users table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "users", schema = "public", catalog = "workshop")
@NamedQueries(
        // Gets all of the users in the database
        @NamedQuery(
                name = "findAllUsers",
                query = "SELECT user FROM UsersEntity user"
        )
)
public class UsersEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private Boolean isAdmin;
    private String email;
    private Double balance;

    /**
     * Gives other classes access to the username attribute
     * @return the name of the user
     */
    @Id
    @Column(name = "username", nullable = false, length = -1)
    public String getUsername() {
        return username;
    }

    /**
     * Gives other classes the ability to modify the username attribute
     * @param username the new name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gives other classes access to the password attribute
     * @return the password of the user
     */
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    /**
     * Gives other classes the ability to modify the password attribute
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gives other classes access to the firstName attribute
     * @return the first name of the user
     */
    @Basic
    @Column(name = "first_name", nullable = false, length = -1)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gives other classes the ability to modify the first name attribute
     * @param firstName the new first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gives other classes access to the lastName attribute
     * @return the last name of the user
     */
    @Basic
    @Column(name = "last_name", nullable = false, length = -1)
    public String getLastName() {
        return lastName;
    }

    /**
     * Gives other classes the ability to modify the last name attribute
     * @param lastName the new last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gives other classes access to the age attribute
     * @return the age of the user
     */
    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    /**
     * Gives other classes the ability to modify the age attribute
     * @param age the new age of the user
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gives other classes access to the address attribute
     * @return the address of the user
     */
    @Basic
    @Column(name = "address", nullable = false, length = -1)
    public String getAddress() {
        return address;
    }

    /**
     * Gives other classes the ability to modify the address attribute
     * @param address the new address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gives other classes access to the isAdmin attribute
     * @return true if the user is an admin and false otherwise
     */
    @Basic
    @Column(name = "is_admin")
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Gives other classes the ability to modify the isAdmin attribute
     * @param isAdmin true if the user is now and admin and false otherwise
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Gives other classes access to the balance attribute
     * @return the balance of the user
     */
    @Basic
    @Column(name = "balance", nullable = true, precision = 0)
    public Double getBalance() {
        return balance;
    }

    /**
     * Gives other classes the ability to modify the balance attribute
     * @param balance the new balance of the user
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (o == null || o instanceof CategoriesEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        UsersEntity that = (UsersEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) &&
                Objects.equals(age, that.age) && Objects.equals(address, that.address) &&
                Objects.equals(isAdmin, isAdmin);
    }

    /**
     * Creates an hash code to identify each user, using its attributes' values
     * @return the hash code for the user
     */
    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string that represents the user (a list of all it's attributes and their values)
     * @return the string representation of the user
     */
    @Override
    public String toString() {
        return "UsersEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", isAdmin=" + isAdmin +
                ", email='" + email + '\'' +
                '}';
    }
}
