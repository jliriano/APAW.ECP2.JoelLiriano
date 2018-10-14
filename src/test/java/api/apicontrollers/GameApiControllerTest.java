package api.apicontrollers;

import api.businesscontrollers.GameBusinessController;
import api.businesscontrollers.PublisherBusinessController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import api.dtos.PublisherDto;
import api.entities.GameRating;
import api.exceptions.ArgumentNotValidException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class GameApiControllerTest {

    private GameBusinessController gameBusinessController;
    private PublisherBusinessController publisherBusinessController;
    private GameApiController gameApiController;
    private GameDto gameDto;
    private PublisherDto publisherDto;
    private String publisherId;
    private String reviewId;

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @BeforeEach
    void setup() {
        gameBusinessController = new GameBusinessController();
        gameApiController = new GameApiController();
        publisherBusinessController = new PublisherBusinessController();
        publisherDto = new PublisherDto("Publisher");
        gameDto = null;
    }

    @Test
    void testFindByCategory() {
        gameApiController.findByCategory("TEEN");
    }

    @Test
    void testFindByCategoryException() {
        assertThrows(ArgumentNotValidException.class, () -> gameApiController.findByCategory("INVALID"));
    }

    @Test
    void testIsValidCategory() {
        assertEquals(true, gameApiController.isValidCategory("TEEN"));
    }

    @Test
    void testIsNotValidCategory() {
        assertEquals(false, gameApiController.isValidCategory("NOPE"));
    }

    @Test
    void testFullDtoCreate() {
        publisherId = publisherBusinessController.create(new PublisherDto("Publisher"));
        gameDto = new GameDto("Game 1", publisherId);
        gameDto.setGameRating(GameRating.EVERYONE.name());
        gameDto.setLaunchDate(LocalDateTime.now());
        String gameId = gameBusinessController.create(gameDto);
        assertNotNull(gameId);
    }

}
