package User.Viet.Modal;

// Feedback.java
public class ItemFeedback {
    private String userName;
    private String feedbackTime;
    private String feedbackContent;

    public ItemFeedback(String userName, String feedbackTime, String feedbackContent) {
        this.userName = userName;
        this.feedbackTime = feedbackTime;
        this.feedbackContent = feedbackContent;
    }

    public String getUserName() {
        return userName;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }
}

