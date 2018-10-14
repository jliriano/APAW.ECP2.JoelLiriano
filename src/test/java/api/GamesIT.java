package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import api.entities.GameRating;
import http.*;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void testFindGameByCategory() {
        String publisherId1 = this.createPublisher();
        String publisherId2 = this.createPublisher();
        String gameId1 = this.createGame("Game 1", publisherId1, null, GameRating.ADULTS_ONLY.toString());
        String gameId2 = this.createGame("Game 2", publisherId1, null, GameRating.ADULTS_ONLY.toString());
        String gameId3 = this.createGame("Game 3", publisherId1, null, GameRating.TEEN.toString());
        String gameId4 = this.createGame("Game 4", publisherId2, null, GameRating.TEEN.toString());
        String gameId5 = this.createGame("Game 5", publisherId2, null, GameRating.EVERYONE.toString());
        String gameId6 = this.createGame("Game 6", publisherId2, null, GameRating.EVERYONE.toString());
        HttpRequest request = HttpRequest.builder(GameApiController.GAMES+GameApiController.SEARCH).param("q","TEEN").get();
        ArrayList<String> results = (ArrayList<String>) new Client().submit(request).getBody();
        LogManager.getLogger().info(results.toString());
        assertEquals(2, results.size());
    }

}
