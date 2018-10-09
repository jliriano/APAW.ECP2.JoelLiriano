package api.dtos;

public class PublisherDto {

    private String name;
    private String website;

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

    @Override
    public String toString() {
        return "PublisherDto{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
