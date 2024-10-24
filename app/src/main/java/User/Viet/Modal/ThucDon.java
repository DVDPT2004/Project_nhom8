package User.Viet.Modal;

public class ThucDon {
    private String avatar, anhmota1, anhmota2, anhmota3, anhmota4;
    private String tinhtrang, tenmonan, motamonan, tenDanhMuc;
    private float giachinh;
    private int phantram;

    public ThucDon(String avatar, String anhmota1, String anhmota2, String anhmota3, String anhmota4, String tinhtrang, float giachinh, String tenmonan, int phantram, String motamonan, String tenDanhMuc) {
        this.avatar = avatar;
        this.anhmota1 = anhmota1;
        this.anhmota2 = anhmota2;
        this.anhmota3 = anhmota3;
        this.anhmota4 = anhmota4;
        this.tinhtrang = tinhtrang;
        this.giachinh = giachinh;
        this.motamonan = motamonan;
        this.phantram = phantram;
        this.tenmonan = tenmonan;
        this.tenDanhMuc = tenDanhMuc; // Thêm tên danh mục
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public String getAnhmota1() {
        return anhmota1;
    }

    public void setAnhmota1(String anhmota1) {
        this.anhmota1 = anhmota1;
    }

    public String getAnhmota2() {
        return anhmota2;
    }

    public void setAnhmota2(String anhmota2) {
        this.anhmota2 = anhmota2;
    }

    public String getAnhmota3() {
        return anhmota3;
    }

    public void setAnhmota3(String anhmota3) {
        this.anhmota3 = anhmota3;
    }

    public String getAnhmota4() {
        return anhmota4;
    }

    public void setAnhmota4(String anhmota4) {
        this.anhmota4 = anhmota4;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getGiachinh() {
        return giachinh;
    }

    public void setGiachinh(float giachinh) {
        this.giachinh = giachinh;
    }

    public String getMotamonan() {
        return motamonan;
    }

    public void setMotamonan(String motamonan) {
        this.motamonan = motamonan;
    }

    public int getPhantram() {
        return phantram;
    }

    public void setPhantram(int phantram) {
        this.phantram = phantram;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
    public float giaGiam() {
        return giachinh * (1 - (phantram / 100.0f)); // Giá sau giảm
    }
}



