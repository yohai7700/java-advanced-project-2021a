package openu.advanced.java_workshop.model;

/**
 * An enum to define the condition of each of the games in the database
 */

public enum Condition {
    BRAND_NEW, LIKE_NEW, GOOD, ACCEPTABLE;

    /**
     * Returns a string representation of each condition
     * @return the string representation of the condition
     */
    @Override
    public String toString() {
        switch(this) {
            case BRAND_NEW: return "Brand New";
            case LIKE_NEW: return "Like New";
            case GOOD: return "Good";
            case ACCEPTABLE: return "Acceptable";
            default: return "None";
        }
    }
}
