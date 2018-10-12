package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import api.dtos.PublisherDto;
import http.Client;
import http.HttpException;
import http.HttpRequest;
import http.HttpStatus;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testCreateGamePublisherNotFoundException() {
        GameDto gameDto = new GameDto("Game Name", "75");
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/75"+GameApiController.GAMES).body(gameDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testCreateGameInvalidArgumentsException() {
        String publisherId = this.createPublisher();
        GameDto gameDto = new GameDto(null,null);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+GameApiController.GAMES).body(gameDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreateGameWithoutName() {
        String publisherId = this.createPublisher();
        GameDto gameDto = new GameDto(null,publisherId);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+GameApiController.GAMES).body(gameDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreateGameWithoutPublisherId() {
        String publisherId = this.createPublisher();
        GameDto gameDto = new GameDto("Game Name",null);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+GameApiController.GAMES).body(gameDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreateGameWithoutGameDto() {
        String publisherId = this.createPublisher();
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+GameApiController.GAMES).body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
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
