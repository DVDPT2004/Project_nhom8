package Admin.Phuoc.admin_feedback.object;

public class Feedback {
    private String thoiGianPhanHoi;
    private String noiDungKhachPhanHoi;
    private int maPhanhoi;
    private String tenKhachPhanHoi;
    private int trangThai;
    private int maDonHang;
    private String noiDungAdminPhanHoi;
    private byte[] media1,media2,media3;

    public Feedback() {
    }

    public Feedback(String noiDungAdminPhanHoi , int trangThai, String thoiGianPhanHoi, String tenKhachPhanHoi, int maPhanhoi, String noiDungKhachPhanHoi) {
        this.trangThai = trangThai;
        this.thoiGianPhanHoi = thoiGianPhanHoi;
        this.tenKhachPhanHoi = tenKhachPhanHoi;
        this.maPhanhoi = maPhanhoi;
        this.noiDungKhachPhanHoi = noiDungKhachPhanHoi;
        this.noiDungAdminPhanHoi = noiDungAdminPhanHoi;
    }
    public Feedback(String noiDungAdminPhanHoi , int trangThai, String thoiGianPhanHoi, String tenKhachPhanHoi, int maPhanhoi, String noiDungKhachPhanHoi, byte[] media1, byte[] media2, byte[] media3) {
        this.trangThai = trangThai;
        this.thoiGianPhanHoi = thoiGianPhanHoi;
        this.tenKhachPhanHoi = tenKhachPhanHoi;
        this.maPhanhoi = maPhanhoi;
        this.noiDungKhachPhanHoi = noiDungKhachPhanHoi;
        this.noiDungAdminPhanHoi = noiDungAdminPhanHoi;
        this.media1 = media1;
        this.media2 = media2;
        this.media3 = media3;
    }

    public byte[] getMedia3() {
        return media3;
    }

    public void setMedia3(byte[] media3) {
        this.media3 = media3;
    }

    public byte[] getMedia2() {
        return media2;
    }

    public void setMedia2(byte[] media2) {
        this.media2 = media2;
    }

    public byte[] getMedia1() {
        return media1;
    }

    public void setMedia1(byte[] media1) {
        this.media1 = media1;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public int getMaPhanhoi() {
        return maPhanhoi;
    }

    public void setMaPhanhoi(int maPhanhoi) {
        this.maPhanhoi = maPhanhoi;
    }

    public String getNoiDungKhachPhanHoi() {
        return noiDungKhachPhanHoi;
    }

    public void setNoiDungKhachPhanHoi(String noiDungPhanHoi) {
        this.noiDungKhachPhanHoi = noiDungPhanHoi;
    }

    public String getTenKhachPhanHoi() {
        return tenKhachPhanHoi;
    }

    public void setTenKhachPhanHoi(String tenKhachPhanHoi) {
        this.tenKhachPhanHoi = tenKhachPhanHoi;
    }

    public String getThoiGianPhanHoi() {
        return thoiGianPhanHoi;
    }

    public void setThoiGianPhanHoi(String thoiGianPhanHoi) {
        this.thoiGianPhanHoi = thoiGianPhanHoi;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getNoiDungAdminPhanHoi() {
        return noiDungAdminPhanHoi;
    }

    public void setNoiDungAdminPhanHoi(String noiDungAdminPhanHoi) {
        this.noiDungAdminPhanHoi = noiDungAdminPhanHoi;
    }
}
