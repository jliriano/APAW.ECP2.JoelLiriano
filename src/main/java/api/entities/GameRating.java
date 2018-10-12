package api.entities;

public enum GameRating {
    PENDING("RATING PENDING"),
    EARLY_CHILDHOOD("EARLY CHILDHOOD"),
    EVERYONE("EVERYONE"),
    EVERYONE_10("EVERYONE 10+"),
    TEEN("TEEN"),
    MATURE("MATURE"),
    ADULTS_ONLY("ADULTS ONLY");

    private String value;

    private GameRating(String value) {
        this.value = value;
    }
}