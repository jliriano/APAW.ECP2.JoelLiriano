package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.ReviewDto;
import api.entities.Publisher;
import api.entities.Review;
import api.exceptions.NotFoundException;

import java.time.LocalDateTime;

public class ReviewBusinessController {

    private PublisherBusinessController publisherBusinessController = new PublisherBusinessController();
    private static final String REVIEW_NOT_FOUND = "Review not found for Publisher";

    public String create(ReviewDto reviewDto, String publisherId) {
        Publisher publisher = publisherBusinessController.getPublisher(publisherId);
        this.processReviewDto(reviewDto);
        Review review = new Review(reviewDto.getReviewMessage(), reviewDto.getTitle(),
                reviewDto.getAuthor(), reviewDto.getReviewRating(), reviewDto.isPendingApproval());
        DaoFactory.getFactory().getReviewDao().save(review);
        publisher.addReview(review.getId());
        DaoFactory.getFactory().getPublisherDao().save(publisher);
        return review.getId();
    }

    public void update(String publisherId, String reviewId, ReviewDto reviewDto) {
        this.read(publisherId, reviewId);
        Publisher publisher = publisherBusinessController.getPublisher(publisherId);
        this.processReviewDto(reviewDto);
        Review review = new Review(reviewDto.getReviewMessage(), reviewDto.getTitle(),
                reviewDto.getAuthor(), reviewDto.getReviewRating(), reviewDto.isPendingApproval());
        review.setId(reviewId);
        DaoFactory.getFactory().getReviewDao().save(review);
        publisher.addReview(review.getId());
    }

    private void processReviewDto(ReviewDto reviewDto) {
        if(reviewDto.getAuthor()==null) {
            reviewDto.setAuthor("Anonymous");
        }
        if(reviewDto.getTitle()==null) {
            reviewDto.setTitle("Untitled");
        }
        if(reviewDto.getPublishedDate()==null) {
            reviewDto.setPublishedDate(LocalDateTime.now());
        }
    }

    public ReviewDto read(String publisherId, String reviewId) {
        if(publisherBusinessController.getPublisher(publisherId).hasReview(reviewId)) {
            Review foundReview = DaoFactory.getFactory().getReviewDao().read(reviewId).orElseThrow(
                    () -> new NotFoundException("[" + reviewId +"] "+REVIEW_NOT_FOUND+" ["+publisherId+"]"));
            return new ReviewDto(foundReview);
        } else throw new NotFoundException("[" + reviewId +"] "+REVIEW_NOT_FOUND+" ["+publisherId+"]");
    }

    public void delete(String publisherId, String reviewId) {
        if(publisherBusinessController.getPublisher(publisherId).hasReview(reviewId)) {
            Publisher publisher = publisherBusinessController.getPublisher(publisherId);
            DaoFactory.getFactory().getReviewDao().deleteById(reviewId);
            publisher.removeReview(reviewId);
            DaoFactory.getFactory().getPublisherDao().save(publisher);
        }
    }

}
