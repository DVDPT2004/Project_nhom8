package Admin.Phuoc.admin_order.object;

public class Order {
    private int maDonHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChiNhan;
    private String thucDon;
    private String ngayDatHang;
    private String tinhTrang;
    private long thanhTien;
    private String phuongThucThanhToan;

    public Order() {
    }

    public Order(String phuongThucThanhToan,long thanhTien, String diaChiNhan, String tinhTrang, String thucDon, String tenKhachHang, String soDienThoai, String ngayDatHang, int maDonHang) {
        this.diaChiNhan = diaChiNhan;
        this.tinhTrang = tinhTrang;
        this.thucDon = thucDon;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.ngayDatHang = ngayDatHang;
        this.maDonHang = maDonHang;
        this.thanhTien = thanhTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getThucDon() {
        return thucDon;
    }

    public void setThucDon(String thucDon) {
        this.thucDon = thucDon;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
