package api.businesscontrollers;

import api.apicontrollers.ReviewApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.PublisherDto;
import api.dtos.ReviewDto;
import api.entities.Publisher;
import api.entities.Review;
import api.exceptions.ArgumentNotValidException;
import api.exceptions.NotFoundException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewBusinessControllerTest {

    private Review review;
    private ReviewBusinessController reviewBusinessController;
    private PublisherBusinessController publisherBusinessController;
    private ReviewDto reviewDto;
    private PublisherDto publisherDto;
    private String publisherId;
    private String reviewId;
    private ReviewApiController reviewApiController = new ReviewApiController();

    @BeforeAll
    static void before() {
            DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @BeforeEach
    void setup() {
        publisherDto = new PublisherDto("Publisher");
        reviewBusinessController = new ReviewBusinessController();
        publisherBusinessController = new PublisherBusinessController();
        reviewDto = null;
        publisherId = null;
        reviewId = null;
    }

    @Test
    void testCreate() {
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto("Review message");
        reviewId = reviewBusinessController.create(reviewDto, publisherId);
        review = this.getReview(reviewId);
        assertEquals("Review message", review.getReviewMessage());
        assertEquals("Untitled", review.getTitle());
        assertEquals("Anonymous", review.getAuthor());
        assertEquals(true, review.isPendingApproval());
    }

    @Test
    void testCreateFull() {
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto("Review message");
        reviewDto.setReviewRating(8);
        reviewDto.setTitle("My title");
        reviewDto.setAuthor("John Doe");
        reviewDto.setPendingApproval(false);
        reviewId = reviewBusinessController.create(reviewDto, publisherId);
        review = this.getReview(reviewId);
        assertEquals("Review message", review.getReviewMessage());
        assertEquals("My title", review.getTitle());
        assertEquals("John Doe", review.getAuthor());
        assertEquals(false, review.isPendingApproval());
    }

    @Test
    void testUpdate() {
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto("Review message");
        reviewDto.setReviewRating(8);
        reviewDto.setTitle("My title");
        reviewDto.setAuthor("John Doe");
        reviewDto.setPendingApproval(false);
        reviewId = reviewBusinessController.create(reviewDto, publisherId);
        review = this.getReview(reviewId);
        reviewDto = new ReviewDto(review);
        reviewDto.setReviewMessage("Updated message");
        reviewDto.setReviewRating(7);
        reviewBusinessController.update(publisherId, reviewId, reviewDto);
        review = this.getReview(reviewId);
        assertEquals(reviewId, review.getId());
        assertEquals("Updated message", review.getReviewMessage());
        assertEquals(7, review.getReviewRating());
    }

    @Test
    void testUpdateNullMessageException() {
        String nullMessage = null;
        publisherId = publisherBusinessController.create(publisherDto);
        reviewDto = new ReviewDto("Review message");
        reviewDto.setReviewRating(8);
        reviewDto.setTitle("My title");
        reviewDto.setAuthor("John Doe");
        reviewDto.setPendingApproval(false);
        reviewId = reviewBusinessController.create(reviewDto, publisherId);
        review = this.getReview(reviewId);
        reviewDto = new ReviewDto(nullMessage);
        reviewDto.setId(reviewId);
        assertThrows(ArgumentNotValidException.class, () -> reviewApiController.updateReview(publisherId, reviewId, reviewDto));
    }

    @Test
    void testReadNotFound() {
        publisherId = publisherBusinessController.create(publisherDto);
        assertThrows(NotFoundException.class, () -> reviewBusinessController.read(publisherId, "3"));
    }


    @Ignore
    private Review getReview(String id) {
        return  DaoFactory.getFactory().getReviewDao().read(id).orElseThrow(
                () -> new NotFoundException("Review (" + id +")"));
    }
}
