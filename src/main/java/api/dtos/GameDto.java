package api.dtos;

import java.time.LocalDateTime;

public class GameDto {

    private String id;
    private String name;
    private String publisherId;
    private LocalDateTime launchDate;
    private String gameRating;

    public GameDto(String name, String publisherId) {
        this.name = name;
        this.publisherId = publisherId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
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

    public String getPublisherId() {
        return publisherId;
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
                ", publisherId='" + publisherId + '\'' +
                ", launchDate=" + launchDate +
                ", gameRating='" + gameRating + '\'' +
                '}';
    }
}
