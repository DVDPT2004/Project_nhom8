package User.Duy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import java.util.Objects;

import Database.MainData.MainData;

public class ActDonHang extends AppCompatActivity {
    TextView txt_madonhang;
    TextView txt_hoten;
    TextView txt_sdt;
    TextView txt_diachi;
    TextView txt_thucdon;
    TextView txt_ngaydathang;
    TextView txt_tinhtrang;
    TextView txt_tongtien;
    TextView txt_thanhtoan;
    Button btn_huy;
    Button btn_xacnhan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_don_hang_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_don_hang_user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txt_madonhang= findViewById(R.id.txt_madonhang);
        txt_hoten = findViewById(R.id.txt_hoten);
        txt_sdt = findViewById(R.id.txt_sdt);
        txt_diachi = findViewById(R.id.txt_diachinhan);
        txt_thucdon = findViewById(R.id.txt_thucdon);
        txt_ngaydathang = findViewById(R.id.txt_ngaydathang);
        txt_tinhtrang = findViewById(R.id.txt_tinhtrang);
        txt_tongtien = findViewById(R.id.txt_tongtien);
        txt_thanhtoan = findViewById(R.id.txt_pttt);
        btn_huy = findViewById(R.id.btn_huy);
        btn_xacnhan = findViewById(R.id.btn_xacnhan);
        initData();
        Intent intent = getIntent();
        int idDonHang = intent.getIntExtra("idDonHang", -1); // check lai id don hang khi ghep code
    }
    private void initData(){
        MainData mainData = new MainData(this);
        Cursor cs = mainData.SelectData("select * from DonHang where maDonHang = 7");
        if(cs != null){
            while(cs.moveToNext()){

                txt_madonhang.setText(cs.getString(0));
                txt_ngaydathang.setText(cs.getString(1));
                txt_tongtien.setText(cs.getString(2));
                txt_diachi.setText(cs.getString(4));
                txt_sdt.setText(cs.getString(5));
                txt_thanhtoan.setText(cs.getString(6));
                txt_hoten.setText(cs.getString(7));

                String tinhtrang = cs.getString(8);
                txt_tinhtrang.setText(tinhtrang);
                // lay ra thuc don
                Cursor cs1 = mainData.SelectData("select tenSanPham, soLuong\n" +
                        "from chiTietDonHang join SanPham \n" +
                        "on chiTietDonHang.maSanPham = SanPham.maSanPham\n" +
                        "where maDonHang = 7");
                String thucdon = "";
                while(cs1.moveToNext()){
                    thucdon += cs1.getString(0) + "- số lượng : " + cs1.getString(1) + "\n";
                }
                txt_thucdon.setText(thucdon);

                if(Objects.equals(tinhtrang, "cho xac nhan")){
                    btn_huy.setVisibility(View.VISIBLE);
                    btn_xacnhan.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "dsadas", Toast.LENGTH_SHORT).show();
                }
                if(Objects.equals(tinhtrang, "da giao hang")){
                    btn_xacnhan.setVisibility(View.VISIBLE);
                    btn_huy.setVisibility(View.INVISIBLE);
                }
            }
        }
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "update DonHang set tinhTrangDonHang = 'Huy'";
                mainData.ExecuteSQL(sql);
                Toast.makeText(ActDonHang.this, "Hủy Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "update DonHang set tinhTrangDonHang = 'Da giao hang'";
                mainData.ExecuteSQL(sql);
                Toast.makeText(ActDonHang.this, "Xacs nhaạn thành công ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
