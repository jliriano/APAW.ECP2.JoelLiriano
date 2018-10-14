package api.apicontrollers;

import api.businesscontrollers.ReviewBusinessController;
import api.dtos.ReviewDto;
import api.exceptions.ArgumentNotValidException;

public class ReviewApiController extends BasicApiController {

    public static final String REVIEWS = "/reviews";

    private ReviewBusinessController reviewBusinessController = new ReviewBusinessController();

    public String create(ReviewDto reviewDto) {
        this.validate(reviewDto, "ReviewDto");
        this.validate(reviewDto.getReviewMessage(), "ReviewMessage");
        if(reviewDto.getReviewRating()<0||reviewDto.getReviewRating()>10) {
            throw new ArgumentNotValidException("Review Rating must be between 1 and 10");
        }
        return this.reviewBusinessController.create(reviewDto);
    }

}
