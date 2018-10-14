package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.apicontrollers.ReviewApiController;
import api.dtos.GameDto;
import api.dtos.PublisherDto;
import api.dtos.ReviewDto;
import http.Client;
import http.HttpRequest;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.time.LocalDateTime;

public class CommonCore {

    protected int publisherReference = 1;

    @Ignore
    protected String createPublisher() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher "+publisherReference++)).post();
        return (String) new Client().submit(request).getBody();
    }

    @Ignore
    protected String createGame(String name, String publisherId, LocalDateTime launchdDate, String gameRating) {
        GameDto gameDto = new GameDto(name, publisherId);
        gameDto.setLaunchDate(launchdDate);
        gameDto.setGameRating(gameRating);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                + GameApiController.GAMES).body(gameDto).post();
        return (String) new Client().submit(request).getBody();
    }

    @Ignore
    protected String createReview(String publisherId, String reviewMessage,
                                  String title, String author, int reviewRating) {
        ReviewDto reviewDto = new ReviewDto(reviewMessage);
        reviewDto.setTitle(title);
        reviewDto.setAuthor(author);
        reviewDto.setReviewRating(reviewRating);
        reviewDto.setPendingApproval(true);
        reviewDto.setPublishedDate(LocalDateTime.now());
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
        + ReviewApiController.REVIEWS).body(reviewDto).post();
        return (String) new Client().submit(request).getBody();
    }

    @Ignore
    protected HttpRequest getPublisher(String id) {
        return HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+id).body("id:'"+id+"'").get();
    }

    @Ignore
    protected HttpRequest getGame(String publisherId, String gameId) {
        return HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId+
                GameApiController.GAMES+"/"+gameId).get();
    }
}
