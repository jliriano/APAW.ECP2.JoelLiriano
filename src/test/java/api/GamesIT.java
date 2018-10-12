package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import http.Client;
import http.HttpException;
import http.HttpRequest;
import http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamesIT extends CommonCore {

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
        String gameId = this.createGame("GameName", publisherId, null, null);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+GameApiController.GAMES +"/"+gameId+GameApiController.NAME)
                .body(new GameDto("NewGameName",null)).patch();

    }

    @Test
    void testPatchGameName() {
        String publisherId = this.createPublisher();
        String gameId = this.createGame("GameName", publisherId, null, null);
        GameDto game = (GameDto) new Client().submit(this.getGame(publisherId, gameId)).getBody();
        assertEquals("GameName", game.getName());
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
        +GameApiController.GAMES+"/"+gameId+GameApiController.NAME).body(new GameDto("NewGameName",null)).patch();
        new Client().submit(request);
        game = (GameDto) new Client().submit(this.getGame(publisherId, gameId)).getBody();
        assertEquals("NewGameName", game.getName());
    }

    @Test
    void testPatchGameWrongGameId() {
        String publisherId = this.createPublisher();
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                +GameApiController.GAMES+"/"+"1"+GameApiController.NAME).body(new GameDto("NewGameName", null)).patch();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testPatchGameWrongPublisherId() {
        String publisherId = this.createPublisher();
        String publisherId2 = this.createPublisher();
        String gameId = this.createGame("NewGame", publisherId, null, null);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/2"
                +GameApiController.GAMES+"/"+gameId+GameApiController.NAME).body(new GameDto("NewerGameName", null)).patch();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testPatchGameBadRequest() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/1"+GameApiController.GAMES
                +"/1"+GameApiController.NAME).body(null).patch();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST,exception.getHttpStatus());
    }

}
