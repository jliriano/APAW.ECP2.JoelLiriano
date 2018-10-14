package api.dtos;

import api.entities.Review;

import java.time.LocalDateTime;

public class ReviewDto {

    private String dtoId;
    private String dotTitle;
    private String dtoAuthor;
    private String dtoReviewMessage;
    private int dtoReviewRating;
    private LocalDateTime dtoPublishedDate;
    private boolean dtoPendingApproval;

    public ReviewDto(String reviewMessage) {
        this.dtoReviewMessage = reviewMessage;
        this.dtoPendingApproval = true;
    }

    public ReviewDto(Review review) {
        this.dtoId = review.getId();
        this.dotTitle = review.getTitle();
        this.dtoAuthor = review.getAuthor();
        this.dtoReviewMessage = review.getReviewMessage();
        this.dtoReviewRating = review.getReviewRating();
        this.dtoPublishedDate = review.getPublishedDate();
        this.dtoPendingApproval = review.isPendingApproval();
    }

    public String getDtoId() {
        return dtoId;
    }

    public void setDtoId(String dtoId) {
        this.dtoId = dtoId;
    }

    public String getDotTitle() {
        return dotTitle;
    }

    public void setDotTitle(String dotTitle) {
        this.dotTitle = dotTitle;
    }

    public String getDtoAuthor() {
        return dtoAuthor;
    }

    public void setDtoAuthor(String dtoAuthor) {
        this.dtoAuthor = dtoAuthor;
    }

    public String getDtoReviewMessage() {
        return dtoReviewMessage;
    }

    public void setDtoReviewMessage(String dtoReviewMessage) {
        this.dtoReviewMessage = dtoReviewMessage;
    }

    public int getDtoReviewRating() {
        return dtoReviewRating;
    }

    public void setDtoReviewRating(int dtoReviewRating) {
        this.dtoReviewRating = dtoReviewRating;
    }

    public LocalDateTime getDtoPublishedDate() {
        return dtoPublishedDate;
    }

    public void setDtoPublishedDate(LocalDateTime dtoPublishedDate) {
        this.dtoPublishedDate = dtoPublishedDate;
    }

    public boolean isDtoPendingApproval() {
        return dtoPendingApproval;
    }

    public void setDtoPendingApproval(boolean dtoPendingApproval) {
        this.dtoPendingApproval = dtoPendingApproval;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "dtoId='" + dtoId + '\'' +
                ", dotTitle='" + dotTitle + '\'' +
                ", dtoAuthor='" + dtoAuthor + '\'' +
                ", dtoReviewMessage='" + dtoReviewMessage + '\'' +
                ", dtoReviewRating=" + dtoReviewRating +
                ", dtoPublishedDate=" + dtoPublishedDate +
                ", dtoPendingApproval=" + dtoPendingApproval +
                '}';
    }
}
