package api.entities;

import java.time.LocalDateTime;

public class Review {

    private String id;
    private String title;
    private String author;
    private String reviewMessage;
    private int reviewRating;
    private LocalDateTime publishedDate;
    private boolean pendingApproval;

    public Review(String reviewMessage, String title,
                  String author, int reviewRating, boolean pendingApproval) {
        this.reviewMessage = reviewMessage;
        this.title = title;
        this.author = author;
        this.reviewRating = reviewRating;
        this.pendingApproval = pendingApproval;
        this.publishedDate = LocalDateTime.now();
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
        return "Review{" +
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
