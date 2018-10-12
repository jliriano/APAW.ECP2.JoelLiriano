package api.dtos;

import api.entities.Publisher;

import java.time.LocalDateTime;

public class GameDto {

    private String id;
    private String name;
    private Publisher publisher;
    private LocalDateTime launchDate;
    private String gameRating;

    public GameDto(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLaunchDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public void setGameRating(String gameRating) {
        this.gameRating = gameRating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public LocalDateTime getLaunchDate() {
        return launchDate;
    }

    public String getGameRating() {
        return gameRating;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", publisher=" + publisher +
                ", launchDate=" + launchDate +
                ", gameRating='" + gameRating + '\'' +
                '}';
    }
}
