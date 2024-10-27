package User.Duy;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.project_nhom8.R;

import java.util.ArrayList;
import java.util.List;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class GioHang {
    private int idSanPham;
    private String tensp;
    private int gia;
    private int soLuong;
    private Uri anhsp;
    private Context context;
    private int user_id;
    public GioHang(Context context) {
        this.context = context;
    }

    public GioHang(int idSanPham, String tensp, int gia, int soLuong, Uri anhsp, int user_id) {
        this.idSanPham = idSanPham;
        this.tensp = tensp;
        this.gia = gia;
        this.soLuong = soLuong;
        this.anhsp = anhsp;
        this.user_id = user_id;
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

    public Uri getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(Uri anhsp) {
        this.anhsp = anhsp;
    }

    public List<GioHang> getAll(){
        List<GioHang> list = new ArrayList<>();
        MainData mainData = new MainData(this.context);
        mainData.getWritableDatabase();
        mainData.getReadableDatabase();
        UserRepository  userRepository = new UserRepository(mainData,context);
        String email = userRepository.getLoggedInUserEmail();
        int user_id = userRepository.getUserIdByEmail(email);
        Cursor cs = mainData.SelectData("Select * from GIOHANG where id_user = " + user_id  );
        // mainData.ExecuteSQL("PRAGMA wal_checkpoint(FULL)"); // Ghi dữ liệu từ WAL vào tệp chính

        while (cs.moveToNext()){
            int id_user = cs.getInt(2);
            int sl = cs.getInt(1);
            int maSanPham = cs.getInt(0);
            Cursor cs1 = mainData.SelectData("Select * from SanPham where maSanPham = " + maSanPham);
            Log.d("check_id", "Select * from SanPham where maSanPham = " + maSanPham + user_id);
            if(cs1 != null){
                while (cs1.moveToNext()) {
                    String tensp = cs1.getString(1);
                    int gia = cs1.getInt(4);
                    String imgString = cs1.getString(8);
                    Log.d("dsa1234", "getAll: " + imgString);
                    Uri imgUri = (imgString != null) ? Uri.parse(imgString) : null;
                    list.add(new GioHang(maSanPham, tensp, gia, sl, imgUri,user_id)); // anh lay ra chua chuan
                }
                cs1.close();
            }
        }
        mainData.close();
        return list;
    }
}
