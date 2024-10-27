package User.Duy.Modal;

import java.io.Serializable;
import java.util.Date;

public class DonHang implements Serializable {
    private int maDonHang;
    private Date ngayGioDatHang;
    private int thanhTien;
    private int userId;
    private String noiGiaoHang;
    private String soDienThoai;
    private String phuongThucThanhToan;
    private String tenKH;
    private int tinhTrangDonHang;

    // Constructor
    public DonHang(int maDonHang, Date ngayGioDatHang, int thanhTien, int userId, String noiGiaoHang,
                   String soDienThoai, String phuongThucThanhToan, String tenKH, int tinhTrangDonHang) {
        this.maDonHang = maDonHang;
        this.ngayGioDatHang = ngayGioDatHang;
        this.thanhTien = thanhTien;
        this.userId = userId;
        this.noiGiaoHang = noiGiaoHang;
        this.soDienThoai = soDienThoai;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tenKH = tenKH;
        this.tinhTrangDonHang = tinhTrangDonHang;
    }

    // Getters and Setters
    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public Date getNgayGioDatHang() {
        return ngayGioDatHang;
    }

    public void setNgayGioDatHang(Date ngayGioDatHang) {
        this.ngayGioDatHang = ngayGioDatHang;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNoiGiaoHang() {
        return noiGiaoHang;
    }

    public void setNoiGiaoHang(String noiGiaoHang) {
        this.noiGiaoHang = noiGiaoHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getTinhTrangDonHang() {
        return tinhTrangDonHang;
    }

    public void setTinhTrangDonHang(int tinhTrangDonHang) {
        this.tinhTrangDonHang = tinhTrangDonHang;
    }
}
