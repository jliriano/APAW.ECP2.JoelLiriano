package api.apicontrollers;

import api.businesscontrollers.PublisherBusinessController;
import api.businesscontrollers.ReviewBusinessController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.PublisherDto;
import api.dtos.ReviewDto;
import api.exceptions.ArgumentNotValidException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewApiControllerTest {

    private ReviewBusinessController reviewBusinessController;
    private PublisherBusinessController publisherBusinessController;
    private ReviewApiController reviewApiController;
    private ReviewDto reviewDto;
    private PublisherDto publisherDto;
    private String publisherId;
    private String reviewId;

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @BeforeEach
    void setup() {
        reviewBusinessController = new ReviewBusinessController();
        reviewApiController = new ReviewApiController();
        publisherBusinessController = new PublisherBusinessController();
        publisherDto = new PublisherDto("Publisher");
        reviewDto = null;
    }

    @Test
    void testCreate() {
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto("Review message");
        reviewId = reviewApiController.create(reviewDto, publisherId);
        reviewDto = reviewApiController.read(publisherId, reviewId);
        assertEquals("Review message", reviewDto.getReviewMessage());
        assertEquals("Untitled", reviewDto.getTitle());
        assertEquals("Anonymous", reviewDto.getAuthor());
        assertEquals(true, reviewDto.isPendingApproval());
        assertEquals(0, reviewDto.getReviewRating());
    }

    @Test
    void testCreateExceptionReviewDtoNull() {
        assertThrows(ArgumentNotValidException.class, () -> reviewApiController.create(reviewDto, publisherId));
    }

    @Test
    void testCreateExceptionMessageNull() {
        String message = null;
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto(message);
        assertThrows(ArgumentNotValidException.class, () -> reviewApiController.create(reviewDto, publisherId));
    }

    @Test
    void testCreateExceptionInvalidRating() {
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto("Review message");
        reviewDto.setReviewRating(99);
        assertThrows(ArgumentNotValidException.class, () -> reviewApiController.create(reviewDto, publisherId));
    }

}
