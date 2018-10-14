package api.dtos;

import java.time.LocalDateTime;

public class ReviewDto {

    private String id;
    private String title;
    private String author;
    private String reviewMessage;
    private int reviewRating;
    private LocalDateTime publishedDate;
    private boolean pendingApproval;

    public ReviewDto(String reviewMessage) {
        this.reviewMessage = reviewMessage;
        this.pendingApproval = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public boolean isPendingApproval() {
        return pendingApproval;
    }

    public void setPendingApproval(boolean pendingApproval) {
        this.pendingApproval = pendingApproval;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", reviewMessage='" + reviewMessage + '\'' +
                ", reviewRating=" + reviewRating +
                ", publishedDate=" + publishedDate +
                ", pendingApproval=" + pendingApproval +
                '}';
    }
}
