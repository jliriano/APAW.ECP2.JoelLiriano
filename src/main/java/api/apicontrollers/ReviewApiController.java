package api.apicontrollers;

import api.businesscontrollers.ReviewBusinessController;
import api.dtos.ReviewDto;
import api.exceptions.ArgumentNotValidException;

public class ReviewApiController extends BasicApiController {

    public static final String REVIEWS = "/reviews";
    public static final String ID_ID = "/{id}";

    private ReviewBusinessController reviewBusinessController = new ReviewBusinessController();

    public String create(ReviewDto reviewDto, String publisherId) {
        this.validate(reviewDto, "ReviewDto");
        this.validate(reviewDto.getReviewMessage(), "ReviewMessage");
        this.validateReviewRating(reviewDto.getReviewRating());
        return this.reviewBusinessController.create(reviewDto, publisherId);
    }

    public void updateReview(String publisherId, String reviewId, ReviewDto reviewDto) {
        this.validate(reviewDto, "ReviewDto");
        this.validate(reviewDto.getReviewMessage(), "ReviewMessage");
        this.validateReviewRating(reviewDto.getReviewRating());
        this.reviewBusinessController.update(publisherId, reviewId, reviewDto);
    }

    private void validateReviewRating(int reviewRating) {
        if(reviewRating<0 || reviewRating>10) {
            throw new ArgumentNotValidException("Review Rating must be between 1 and 10");
        }
    }

    public ReviewDto read(String publisherId, String reviewId) {
        this.validate(publisherId, "publisherId");
        this.validate(reviewId,"reviewId");
        return this.reviewBusinessController.read(publisherId, reviewId);
    }

}
