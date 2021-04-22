package openu.advanced.java_workshop.model;

import openu.advanced.java_workshop.ImagesRepository;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Entity class for the games table in the database
 * Defines several queries and allows other classes to have access to the table's values
 */

@Entity
@Table(name = "games", schema = "public", catalog = "workshop")
@NamedQueries({
        // Returns all of the games in the table
        @NamedQuery(name = "getAllGames", query = "SELECT game FROM GamesEntity game"),
        // Returns the game which matches the given id
        @NamedQuery(name = "getGameById", query = "SELECT game FROM GamesEntity game WHERE game.id =:id"),
        // Returns all of the categories of the game that matches the given id
        @NamedQuery(name = "getCategoriesByGameId",
            query = "SELECT category FROM CategoriesEntity category " +
                    "WHERE EXISTS " +
                    "(SELECT categoryMember FROM CategoryMembersEntity categoryMember " +
                        "WHERE categoryMember.categoryId=category.id AND categoryMember.gameId=:gameId)"),
        // Returns all of the recommendations of the game with the given id
        @NamedQuery(name = "getRecommendationsByGameId",
            query = "SELECT game FROM GamesEntity game " +
                    "WHERE game.id <> :gameId AND EXISTS " +
                        "(SELECT categoryMember FROM CategoryMembersEntity categoryMember " +
                        "WHERE categoryMember.gameId = game.id AND " +
                            "EXISTS (SELECT m FROM CategoryMembersEntity m " +
                            "WHERE m.gameId = :gameId AND m.categoryId = categoryMember.categoryId))"),
        // Searches games that their names contain the given subword (not case-senstive)
        @NamedQuery(name = "searchGames",
                query = "SELECT game FROM GamesEntity game WHERE LOWER(game.name) LIKE LOWER(CONCAT('%', :name, '%'))")
})
public class GamesEntity {
    private int id;
    private String name;
    private String publisher;
    private String developer;
    private Date releaseDate;
    private double price;
    private int stock;
    private Condition condition;

    /**
     * Gives other classes access to the id attribute
     * @return the id of the game
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Gives other classes the ability to modify the id attribute
     * @param id the new id of the game
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gives other classes access to the name attribute
     * @return the name of the game
     */
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    /**
     * Gives other classes the ability to modify the name attribute
     * @param name the new name of the game
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gives other classes access to the publisher attribute
     * @return the publisher of the game
     */
    @Basic
    @Column(name = "publisher", nullable = false, length = 20)
    public String getPublisher() {
        return publisher;
    }

    /**
     * Gives other classes the ability to modify the publisher attribute
     * @param publisher the new publisher of the game
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gives other classes access to the developer attribute
     * @return the developer of the game
     */
    @Basic
    @Column(name = "developer", nullable = false, length = 20)
    public String getDeveloper() {
        return developer;
    }

    /**
     * Gives other classes the ability to modify the developer attribute
     * @param developer the new developer of the game
     */
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    /**
     * Gives other classes access to the releaseDate attribute
     * @return the release date of the game
     */
    @Basic
    @Column(name = "release_date", nullable = false)
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gives other classes the ability to modify the release date attribute
     * @param releaseDate the new developer of the game
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the release date of the game, but in a java.util.Date form (for usage in xhtml components)
     * @return the release date of the game in java.util.Date form
     */
    @Transient
    public java.util.Date getSimpleReleaseDate(){
        if(releaseDate == null) {
            return null;
        }
        return new java.util.Date(releaseDate.getTime());
    }

    /**
     * Sets the release date of the game, but in a java.util.Date form (for usage in xhtml components)
     * @param releaseDate the new release date in java.util.Date form
     */
    public void setSimpleReleaseDate(java.util.Date releaseDate) {
        if(releaseDate == null) {
            this.releaseDate = null;
            return;
        }
        this.releaseDate = new java.sql.Date(releaseDate.getTime());
    }

    /**
     * Gives other classes access to the price attribute
     * @return the price of the game
     */
    @Basic
    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }

    /**
     * Gives other classes the ability to modify the price attribute
     * @param price the new price of the game
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gives other classes access to the stock attribute
     * @return the stock of the game
     */
    @Basic
    @Column(name = "stock", nullable = false)
    public int getStock() {
        return stock;
    }

    /**
     * Gives other classes the ability to modify the stock attribute
     * @param stock the new stock of the game
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gives other classes access to the condition attribute
     * @return the condition of the game
     */
    @Basic
    @Column(name = "condition", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    public Condition getCondition() {
        return condition;
    }

    /**
     * Gives other classes the ability to modify the condition attribute
     * @param condition the new condition of the game
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Transient
    public String getReleaseDisplayDate(){
        java.util.Date releaseDate = getSimpleReleaseDate();
        if(releaseDate == null){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(releaseDate);
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
        if (o == null || o instanceof GamesEntity) return false;

        // We can now upcast o to this class (won't cause an error because o is an instance of this class)
        GamesEntity that = (GamesEntity) o;

        // If the attributes of the objects are equal, the objects are equal
        return id == that.id && Double.compare(that.price, price) == 0 && stock == that.stock &&
                Objects.equals(name, that.name) && Objects.equals(publisher, that.publisher) &&
                Objects.equals(developer, that.developer) && Objects.equals(condition, that.condition);
    }

    /**
     * Creates an hash code to identify each game, using all of it's attributes
     * @return the hash code for the game
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (developer != null ? developer.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        long temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + stock;
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string that represents the game (a list of all attributes of the game with it's values)
     * @return the string representation of the game
     */
    @Override
    public String toString() {
        return "GamesEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publisher='" + publisher + '\'' +
                ", developer='" + developer + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", stock=" + stock +
                ", condition=" + condition +
                '}';
    }

    /**
     * Returns the path of the image of the game
     * @param gameId the id of the game we want it's image path
     * @return the path of the game's image
     */
    public static String getImagePath(int gameId){
        return "game\\" + gameId;
    }

    /**
     * Transient field for the game image path.
     * @return the path of the game's image
     */
    @Transient
    public String getImagePath(){
        return GamesEntity.getImagePath(id);
    }

    /**
     * Sets game's image persistently.
     * @param image input stream for the image
     * @throws IOException if fails to save the image in the file system.
     */
    public void setImage(InputStream image) throws IOException {
        ImagesRepository.uploadImage(getImagePath(), image);
    }

    /**
     * Transient field that returns whether the game has an image.
     * @return whether the game has an image saved.
     */
    @Transient
    public boolean hasImage(){
        return ImagesRepository.isExists(getImagePath());
    }
}
