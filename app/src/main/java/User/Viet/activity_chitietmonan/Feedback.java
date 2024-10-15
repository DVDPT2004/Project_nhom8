package User.Viet.activity_chitietmonan;

public class Feedback {
    private String userName;
    private String feedbackDate;
    private String feedbackContent;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;

    // Constructor
    public Feedback(String userName, String feedbackDate, String feedbackContent, byte[] image1, byte[] image2, byte[] image3) {
        this.userName = userName;
        this.feedbackDate = feedbackDate;
        this.feedbackContent = feedbackContent;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    // Getter methods
    public String getUserName() {
        return userName;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public byte[] getImage1() {
        return image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public byte[] getImage3() {
        return image3;
    }
}

