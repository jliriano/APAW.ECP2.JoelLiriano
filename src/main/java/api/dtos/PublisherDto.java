package api.dtos;

import api.entities.Publisher;

import java.util.ArrayList;
import java.util.List;

public class PublisherDto {

    private String id;
    private String name;
    private String website;
    private List<String> games = new ArrayList<>();

    public PublisherDto(Publisher publisher) {
        this.name = publisher.getName();
        this.id = publisher.getId();
        this.website = publisher.getWebsite();
    }

    public PublisherDto(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    public void addGame(String gameId) {
        games.add(gameId);
    }

    public boolean hasGame(String gameId) {
        return games.contains(gameId);
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PublisherDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", games=" + games +
                '}';
    }
}
