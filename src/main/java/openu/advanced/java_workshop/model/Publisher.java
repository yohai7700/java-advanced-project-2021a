package openu.advanced.java_workshop.model;

public enum Publisher {
    EA, ROCKSTAR, UBISOFT, LUCASARTS;

    @Override
    public String toString() {
        switch (this){
            case EA: return "Electronic Arts";
            case UBISOFT: return "Ubisoft";
            case ROCKSTAR: return "Rockstar Games";
            case LUCASARTS: return "Lucas Arts";
            default: return "None";
        }
    }
}
