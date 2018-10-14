package api;

import api.apicontrollers.PublisherApiController;
import api.apicontrollers.ReviewApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.ReviewDto;
import http.*;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewsIT extends CommonCore {

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @Test
    void testCreateBasicReview() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String reviewId = this.createReview(publisherId, "This is my review message",
                null, null, 0);
        assertNotNull(reviewId);
        LogManager.getLogger().info(new Client().submit(this.getPublisher(publisherId)).getBody());
    }

    @Test
    void testCreateFullReview() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String reviewId = this.createReview(publisherId, "This is my review message",
                "My Review Title", "Author McPhee", 9);
        assertNotNull(reviewId);
        LogManager.getLogger().info(new Client().submit(this.getPublisher(publisherId)).getBody());
    }

    @Test
    void testCreateReviewPublisherNotFoundException() {
        ReviewDto reviewDto = new ReviewDto("Review message");
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/75"+ ReviewApiController.REVIEWS).body(reviewDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testCreateReviewInvalidReviewRating() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        ReviewDto reviewDto = new ReviewDto("Review message");
        reviewDto.setDtoReviewRating(19);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+ReviewApiController.REVIEWS).body(reviewDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreateReviewInvalidReviewMessage() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        ReviewDto reviewDto = null;
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+ReviewApiController.REVIEWS).body(reviewDto).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testUpdateReview() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String reviewId = this.createReview(publisherId, "This is my review message",
                null, null, 0);
        ReviewDto review = (ReviewDto) new Client().submit(this.getReview(publisherId, reviewId)).getBody();
        assertEquals("This is my review message", review.getDtoReviewMessage());
        assertEquals(reviewId,review.getDtoId());
        assertEquals("Anonymous", review.getDtoAuthor());
        assertEquals("Untitled",review.getDotTitle());
        assertEquals(true, review.isDtoPendingApproval());
        review.setDtoAuthor("Jon Snow");
        review.setDotTitle("Westeros");
        review.setDtoReviewMessage("Updated review message");
        review.setDtoReviewRating(8);
        review.setDtoPendingApproval(false);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
        +ReviewApiController.REVIEWS+"/"+reviewId).body(review).put();
        new Client().submit(request);
        review = (ReviewDto) new Client().submit(this.getReview(publisherId, reviewId)).getBody();
        assertEquals("Updated review message", review.getDtoReviewMessage());
        assertEquals(reviewId,review.getDtoId());
        assertEquals("Jon Snow", review.getDtoAuthor());
        assertEquals("Westeros",review.getDotTitle());
        assertEquals(false, review.isDtoPendingApproval());
    }

    @Test
    void testUpdateReviewInvalidArgumentsReviewMessage() {
        String newMessage = null;
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String reviewId = this.createReview(publisherId, "This is my review message",
                null, null, 0);
        ReviewDto reviewDto = new ReviewDto(newMessage);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+ReviewApiController.REVIEWS+"/"+reviewId).body(reviewDto).put();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testUpdateReviewInvalidArgumentsReviewRating() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String reviewId = this.createReview(publisherId, "This is my review message",
                null, null, 0);
        ReviewDto reviewDto = new ReviewDto("Updated message");
        reviewDto.setDtoReviewRating(33);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+ReviewApiController.REVIEWS+"/"+reviewId).body(reviewDto).put();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testUpdateReviewNotFound() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        ReviewDto reviewDto = new ReviewDto("New message");
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/"+publisherId+ReviewApiController.REVIEWS+"/666").body(reviewDto).put();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testUpdateReviewPublisherNotFound() {
        ReviewDto reviewDto = new ReviewDto("New message");
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS
                +"/666"+ReviewApiController.REVIEWS+"/1").body(reviewDto).put();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testDeleteReview() {
        String publisherId = this.createPublisher();
        String reviewId = this.createReview(publisherId, "Review message", null, null, 0);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
        +ReviewApiController.REVIEWS+"/"+reviewId).delete();
        new Client().submit(request);
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(this.getReview(publisherId, reviewId)));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testDeleteAlreadyDeletedReview() {
        String publisherId = this.createPublisher();
        String reviewId = this.createReview(publisherId, "Review message", null, null, 0);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
                +ReviewApiController.REVIEWS+"/"+reviewId).delete();
        new Client().submit(request);
        HttpResponse response = new Client().submit(request);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

}
