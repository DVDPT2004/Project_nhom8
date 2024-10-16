package User.Viet.Modal;

public class ThucDon {
    private int avatar;       // Hình ảnh món ăn
    private float giachinh;   // Giá chính
    private int phantram;     // Phần trăm giảm giá
    private String tenmonan;  // Tên món ăn
    private String motamonan;
    private int anhmota1;
    private int anhmota2;
    private int anhmota3;
    private int anhmota4;
    private int madanhmuc;
    private String tinhtrang;

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }



    // Constructor với các tham số tương ứng


    public ThucDon(int anhmota1, int anhmota2, int anhmota3, int anhmota4, int avatar,String tinhtrang, float giachinh, String tenmonan, int phantram, String motamonan,int madanhmuc) {
        this.anhmota1 = anhmota1;
        this.anhmota2 = anhmota2;
        this.anhmota3 = anhmota3;
        this.anhmota4 = anhmota4;
        this.avatar = avatar;
        this.tinhtrang=tinhtrang;
        this.giachinh = giachinh;
        this.motamonan = motamonan;
        this.phantram = phantram;
        this.tenmonan = tenmonan;
        this.madanhmuc=madanhmuc;
    }

    public int getAnhmota1() {
        return anhmota1;
    }

    public void setAnhmota1(int anhmota1) {
        this.anhmota1 = anhmota1;
    }

    public int getAnhmota2() {
        return anhmota2;
    }

    public void setAnhmota2(int anhmota2) {
        this.anhmota2 = anhmota2;
    }

    public int getAnhmota3() {
        return anhmota3;
    }

    public void setAnhmota3(int anhmota3) {
        this.anhmota3 = anhmota3;
    }

    public int getAnhmota4() {
        return anhmota4;
    }

    public void setAnhmota4(int anhmota4) {
        this.anhmota4 = anhmota4;
    }

    public String getMotamonan() {
        return motamonan;
    }

    public void setMotamonan(String motamonan) {
        this.motamonan = motamonan;
    }

    // Getter cho các thuộc tính
    public int getAvatar() {
        return avatar;
    }

    public float getGiachinh() {
        return giachinh;
    }

    public int getPhantram() {
        return phantram;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    // Phương thức tính giá sau giảm
    public float giaGiam() {
        return giachinh * (1 - (phantram / 100.0f)); // Giá sau giảm
    }

    public int getMadanhmuc() {
        return madanhmuc;
    }

    public void setMadanhmuc(int madanhmuc) {
        this.madanhmuc = madanhmuc;
    }
}

