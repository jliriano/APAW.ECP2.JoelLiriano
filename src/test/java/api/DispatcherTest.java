package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.apicontrollers.ReviewApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import api.dtos.PublisherDto;
import api.dtos.ReviewDto;
import api.entities.Publisher;
import http.Client;
import http.HttpException;
import http.HttpRequest;
import http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DispatcherTest extends CommonCore {

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @Test
    void testPostPublisher() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher 1")).post();
        assertEquals(HttpStatus.OK, new Client().submit(request).getStatus());
    }

    @Test
    void testPostGame() {
        HttpRequest requestPublisher = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher PostGame")).post();
        String publisherId = (String) new Client().submit(requestPublisher).getBody();
        HttpRequest requestGame = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId+ GameApiController.GAMES)
                .body(new GameDto("PostGame", publisherId)).post();
        assertEquals(HttpStatus.OK, new Client().submit(requestGame).getStatus());
    }

    @Test
    void testPostReview() {
        HttpRequest requestPublisher = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher PostGame")).post();
        String publisherId = (String) new Client().submit(requestPublisher).getBody();
        HttpRequest requestReview = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                + ReviewApiController.REVIEWS).body( new ReviewDto("Review Message")).post();
        assertEquals(HttpStatus.OK, new Client().submit(requestReview).getStatus());
    }

    @Test
    void testPostReviewBadRequest() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/1"+ReviewApiController.REVIEWS).body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testPostGameBadRequest() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/1"+GameApiController.GAMES).body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testPostPublisherBadRequest() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/invalid").body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testPostPublisherWithoutPublisherDto() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testGetPublisherNotFoundError() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/238").get();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testGetPublisherInvalidRequest(){
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/").get();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testGetGameNotFoundError() {
        String publisherId = this.createPublisher();
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
        +GameApiController.GAMES+"/1").get();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testPatchGameName() {
        String publisherId = this.createPublisher();
        String gameId = this.createGame("Game1", publisherId, null, null);
        GameDto game = (GameDto) new Client().submit(this.getGame(publisherId, gameId)).getBody();
        assertEquals("Game1", game.getName());
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                +GameApiController.GAMES+"/"+gameId+GameApiController.NAME).body(new GameDto("NewGameName",null)).patch();
        new Client().submit(request);
        game = (GameDto) new Client().submit(this.getGame(publisherId, gameId)).getBody();
        assertEquals("NewGameName", game.getName());
    }

    @Test
    void testPatchGameNameGameNotFound() {
        String publisherId = this.createPublisher();
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                +GameApiController.GAMES+"/"+"1"+GameApiController.NAME).body(new GameDto("NewGameName", null)).patch();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testPostMethodError() {
        HttpRequest request = HttpRequest.builder("/METHODERROR").body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testGetMethodError() {
        HttpRequest request = HttpRequest.builder("/METHODERROR").body(null).get();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testPutMethodError() {
        HttpRequest request = HttpRequest.builder("/METHODERROR").body(null).put();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testPatchMethodError() {
        HttpRequest request = HttpRequest.builder("/METHODERROR").body(null).patch();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testDeleteMethodError() {
        HttpRequest request = HttpRequest.builder("/METHODERROR").body(null).delete();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testDeleteReview() {
        HttpRequest requestPublisher = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher PostGame")).post();
        String publisherId = (String) new Client().submit(requestPublisher).getBody();
        HttpRequest requestReview = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                + ReviewApiController.REVIEWS).body( new ReviewDto("Review Message")).post();
        String reviewId = (String) new Client().submit(requestReview).getBody();
        HttpRequest deleteReview = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                + ReviewApiController.REVIEWS + ReviewApiController.ID_ID ).delete();
        assertEquals(HttpStatus.OK, new Client().submit(requestReview).getStatus());
    }

}
