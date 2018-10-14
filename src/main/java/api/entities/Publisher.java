package api.entities;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    private String id;
    private String name;
    private String website;
    private List<String> games = new ArrayList<>();
    private List<String> reviews = new ArrayList<>();

    public Publisher(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addGame(String gameId) {
        games.add(gameId);
    }

    public boolean hasGame(String gameId) {
        return games.contains(gameId);
    }

    public List<String> getGames() {
        return games;
    }

    public void addReview(String reviewId) {
        reviews.add(reviewId);
    }

    public void removeReview(String reviewId) {
        reviews.remove(reviewId);
    }

    public boolean hasReview(String reviewId) {
        return reviews.contains(reviewId);
    }

    public List<String> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", games=" + games +
                ", reviews=" + reviews +
                '}';
    }
}