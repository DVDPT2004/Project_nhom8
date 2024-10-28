package User.Duy.Views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_nhom8.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import Database.MainData.MainData;
import User.Duy.Modal.DonHang;
import User.Viet.activity_phanhoi.PhanHoi;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView txtMaDonHang;
    private TextView txtTenKH;
    private TextView txtSoDienThoai;
    private TextView txtDiaChiNhan;
    private TextView txtThucDon;
    private TextView txtNgayDatHang;
    private TextView txtTinhTrang;
    private TextView txtTongTien;
    private TextView txtPhuongThucThanhToan;
    private Button btnCancel;
    private  Button btnPhanHoi;
    private ImageButton btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);

        // Ánh xạ các thành phần trong layout
        txtMaDonHang = findViewById(R.id.txt_madonhang);
        txtTenKH = findViewById(R.id.txt_hoten);
        txtSoDienThoai = findViewById(R.id.txt_sdt);
        txtDiaChiNhan = findViewById(R.id.txt_diachinhan);
        txtThucDon = findViewById(R.id.txt_thucdon);
        txtNgayDatHang = findViewById(R.id.txt_ngaydathang);
        txtTinhTrang = findViewById(R.id.txt_tinhtrang);
        txtTongTien = findViewById(R.id.txt_tongtien);
        txtPhuongThucThanhToan = findViewById(R.id.txt_pttt);
        btnCancel = findViewById(R.id.btnCancel);
        btnPhanHoi = findViewById(R.id.user_phan_hoi);
        btnExit = findViewById(R.id.btn_exit);
        // Lấy thông tin đơn hàng từ Intent
        DonHang donHang = (DonHang) getIntent().getSerializableExtra("DON_HANG");

        //check trang thai don hang:

        // Hiển thị thông tin đơn hàng
        if (donHang != null) {
            txtMaDonHang.setText(String.valueOf(donHang.getMaDonHang()));
            txtTenKH.setText(donHang.getTenKH());
            txtSoDienThoai.setText(donHang.getSoDienThoai());
            txtDiaChiNhan.setText(donHang.getNoiGiaoHang());
            txtThucDon.setText(getThucDon(donHang.getMaDonHang())); // Bạn có thể cập nhật thực đơn nếu có
            txtNgayDatHang.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(donHang.getNgayGioDatHang()));
            txtTinhTrang.setText(getTinhTrang(donHang.getTinhTrangDonHang()));
            txtTongTien.setText(String.format("%d VNĐ", donHang.getThanhTien()));
            txtPhuongThucThanhToan.setText(donHang.getPhuongThucThanhToan());
        }

        // Xử lý sự kiện hủy đơn
        btnCancel.setOnClickListener(view -> {
            int maDonHang = donHang.getMaDonHang();
            String sql = "Update DonHang set tinhTrangDonHang = 3 where maDonHang = " +maDonHang;
            MainData mainData = new MainData(this);
            mainData.getWritableDatabase();
            mainData.ExecuteSQL(sql);
            mainData.close();
            btnCancel.setVisibility(View.INVISIBLE);
            txtTinhTrang.setText(getTinhTrang(3).toString());
        });
        btnPhanHoi.setOnClickListener(view -> {
            int maDonHang = donHang.getMaDonHang();
            String sql = "Select count(*) from PhanHoi where maDonHang <> " +maDonHang;
            MainData mainData = new MainData(this);
            mainData.getWritableDatabase();
            Cursor cs = mainData.SelectData(sql);
            while(cs.moveToNext()){
                if(cs.getInt(0) == 0){
                    Intent intent = new Intent(OrderDetailActivity.this, PhanHoi.class);
                    intent.putExtra("maDonHang", maDonHang);
                    startActivity(intent);
                }else{
                    Toast.makeText(OrderDetailActivity.this,"đơn hàng đã được phản hồi",Toast.LENGTH_SHORT).show();
                }
            }
            mainData.close();
        });
        btnExit.setOnClickListener(view -> {
            finish();
        });
    }
    private String getTinhTrang(int trangthai_int){
        String trangthai_string = "";
        if(trangthai_int == 0){
            trangthai_string = "chưa chuẩn bị";
            btnCancel.setVisibility(View.VISIBLE);
        }
        else if(trangthai_int == 1){
            trangthai_string = "đang chuẩn bị";
            btnCancel.setVisibility(View.INVISIBLE);
        }
        else if(trangthai_int == 2){
            trangthai_string = "giao hàng thành công";
            btnPhanHoi.setVisibility(View.VISIBLE);
        }
        else if(trangthai_int == 3){
            trangthai_string = "hủy đơn hàng";
            btnCancel.setVisibility(View.INVISIBLE);
        }
        return trangthai_string;
    }
    private String getThucDon(int maDonHang){
        String sql = "Select tenSanPham, soLuong from \n" +
                "ChiTietDonHang join SanPham on ChiTietDonHang.maSanPham = SanPham.maSanPham \n" +
                "join DonHang on DonHang.maDonHang = ChiTietDonHang.maDonHang where DonHang.maDonHang = " + maDonHang;
        MainData mainData = new MainData(this);
        mainData.getReadableDatabase();
        Cursor cs = mainData.SelectData(sql);
        String thucdon = "";
        if(cs != null){
            while(cs.moveToNext()){
                thucdon += cs.getString(0) + "- số lượng : " + cs.getString(1) + "\n";
            }
        }
        return thucdon;
    }
}
