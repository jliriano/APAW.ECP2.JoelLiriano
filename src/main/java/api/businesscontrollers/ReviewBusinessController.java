package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.ReviewDto;
import api.entities.Publisher;
import api.entities.Review;

import java.time.LocalDateTime;

public class ReviewBusinessController {

    private PublisherBusinessController publisherBusinessController = new PublisherBusinessController();

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

}
