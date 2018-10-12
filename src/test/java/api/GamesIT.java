package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import api.dtos.PublisherDto;
import http.Client;
import http.HttpRequest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

public class GamesIT {

    private int publisherReference = 1;

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @Test
    void testCreateGame() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String gameId = this.createGame("Game 1", publisherId, null, null);
        LogManager.getLogger().info(new Client().submit(this.getPublisher(publisherId)).getBody());
    }

    @Ignore
    private String createGame(String name, String publisherId, LocalDateTime launchdDate, String gameRating) {
        GameDto gameDto = new GameDto(name, publisherId);
        gameDto.setLaunchDate(launchdDate);
        gameDto.setGameRating(gameRating);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                +GameApiController.GAMES).body(gameDto).post();
        return (String) new Client().submit(request).getBody();
    }

    @Ignore
    private HttpRequest getPublisher(String id) {
        return HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+id).body("id:'"+id+"'").get();
    }

    @Ignore
    private String createPublisher() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher "+publisherReference++)).post();
        return (String) new Client().submit(request).getBody();
    }

}
