package User.Duy;

public class GioHang {
    private int idSanPham;
    private String tensp;
    private long gia;
    private int soLuong;
    private int anhsp;

    public GioHang(int idSanPham, String tensp, long gia, int soLuong, int anhsp) {
        this.idSanPham = idSanPham;
        this.tensp = tensp;
        this.gia = gia;
        this.soLuong = soLuong;
        this.anhsp = anhsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(int anhsp) {
        this.anhsp = anhsp;
    }
}
