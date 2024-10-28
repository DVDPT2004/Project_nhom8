package User.Viet.Modal;

public class Feedback {
    private String userName;
    private String feedbackDate;
    private String feedbackContent;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;
    private String noiDungAdminPhanHoi;

    // Constructor
    public Feedback(String noiDungAdminPhanHoi, String userName, String feedbackDate, String feedbackContent, byte[] image1, byte[] image2, byte[] image3) {
        this.userName = userName;
        this.feedbackDate = feedbackDate;
        this.feedbackContent = feedbackContent;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.noiDungAdminPhanHoi = noiDungAdminPhanHoi;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getNoiDungAdminPhanHoi() {
        return noiDungAdminPhanHoi;
    }

    public void setNoiDungAdminPhanHoi(String noiDungAdminPhanHoi) {
        this.noiDungAdminPhanHoi = noiDungAdminPhanHoi;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
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
