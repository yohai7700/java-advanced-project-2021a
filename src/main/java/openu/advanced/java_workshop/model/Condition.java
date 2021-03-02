package openu.advanced.java_workshop.model;

public enum Condition {
    BRAND_NEW, LIKE_NEW, GOOD, ACCEPTABLE;

    @Override
    public String toString() {
        switch (this){
            case BRAND_NEW: return "Brand New";
            case LIKE_NEW: return "Like New";
            case GOOD: return "Good";
            case ACCEPTABLE: return "Acceptable";
            default: return "None";
        }
    }
}
