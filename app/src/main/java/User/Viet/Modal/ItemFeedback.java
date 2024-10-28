package User.Viet.Modal;

// Feedback.java
public class ItemFeedback {
    private String userName;
    private String feedbackTime;
    private String feedbackContent;
    private int maDonHang; // Thêm biến mã đơn hàng
    private String ngayGioDatHang; // Thêm biến thời gian đặt hàng

    public ItemFeedback(String userName, String feedbackTime, String feedbackContent, int maDonHang, String ngayGioDatHang) {
        this.userName = userName;
        this.feedbackTime = feedbackTime;
        this.feedbackContent = feedbackContent;
        this.maDonHang = maDonHang; // Gán mã đơn hàng
        this.ngayGioDatHang = ngayGioDatHang; // Gán thời gian đặt hàng
    }

    // Thêm các getter cho các thuộc tính mới
    public int getMaDonHang() {
        return maDonHang;
    }

    public String getNgayGioDatHang() {
        return ngayGioDatHang;
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

