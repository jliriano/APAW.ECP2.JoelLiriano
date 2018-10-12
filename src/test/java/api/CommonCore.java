package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.dtos.GameDto;
import api.dtos.PublisherDto;
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
    protected HttpRequest getPublisher(String id) {
        return HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+id).body("id:'"+id+"'").get();
    }

    @Ignore
    protected HttpRequest getGame(String publisherId, String gameId) {
        return HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId+
                GameApiController.GAMES+"/"+gameId).get();
    }
}
