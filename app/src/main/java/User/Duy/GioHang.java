package User.Duy;

import android.content.Context;
import android.database.Cursor;

import com.example.project_nhom8.R;

import java.util.ArrayList;
import java.util.List;

import Database.MainData.MainData;

public class GioHang {
    private int idSanPham;
    private String tensp;
    private int gia;
    private int soLuong;
    private int anhsp;
    private Context context;

    public GioHang(Context context) {
        this.context = context;
    }

    public GioHang(int idSanPham, String tensp, int gia, int soLuong, int anhsp) {
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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
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

    public List<GioHang> getAll(){
        List<GioHang> list = new ArrayList<>();
        DBGioHangManager dbGioHangManager = new DBGioHangManager(context);
        MainData mainData = new MainData(context);
        Cursor cs = dbGioHangManager.selectData("select * from giohang");
        while(cs.moveToNext()){
            int id_sp = cs.getInt(0);
            int sl = cs.getInt(1);
            //GioHang(int idSanPham, String tensp, long gia, int soLuong, int anhsp
            String sql = "Select * from SanPham where maSanPham = " + id_sp;
            Cursor cs1 = mainData.SelectData(sql);
            if (cs1 != null) {
                while (cs1.moveToNext()) {
                    String tensp = cs1.getString(1);
                    int gia = cs1.getInt(4);
                    list.add(new GioHang(id_sp, tensp, gia, sl, R.drawable.banhmi)); // anh lay ra chua chuan
                }
                cs1.close();
            }
        }
        cs.close();
        return list;
    }
}
