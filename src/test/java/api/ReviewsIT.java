package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.apicontrollers.ReviewApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.GameDto;
import api.dtos.ReviewDto;
import api.entities.Publisher;
import http.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
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
        reviewDto.setReviewRating(19);
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
        assertEquals("This is my review message", review.getReviewMessage());
        assertEquals(reviewId,review.getId());
        assertEquals("Anonymous", review.getAuthor());
        assertEquals("Untitled",review.getTitle());
        assertEquals(true, review.isPendingApproval());
        review.setAuthor("Jon Snow");
        review.setTitle("Westeros");
        review.setReviewMessage("Updated review message");
        review.setReviewRating(8);
        review.setPendingApproval(false);
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/"+publisherId
        +ReviewApiController.REVIEWS+"/"+reviewId).body(review).put();
        new Client().submit(request);
        review = (ReviewDto) new Client().submit(this.getReview(publisherId, reviewId)).getBody();
        assertEquals("Updated review message", review.getReviewMessage());
        assertEquals(reviewId,review.getId());
        assertEquals("Jon Snow", review.getAuthor());
        assertEquals("Westeros",review.getTitle());
        assertEquals(false, review.isPendingApproval());
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
        reviewDto.setReviewRating(33);
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
