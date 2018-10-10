package api.dtos;

import api.entities.Publisher;

public class PublisherDto {

    private String id;
    private String name;
    private String website;

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
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
