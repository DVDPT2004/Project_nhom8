package User.Duy;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class ActGioHang extends AppCompatActivity {
    GridView gvGioHang;
    Button btn_dathang;
    List<GioHang> list;
    GioHangAdapter adapter;
    TextView txtTongTien;
    LinearLayout act_gio_hang_user;
    private UserRepository  userRepository;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_gio_hang_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_gio_hang_user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        list = new ArrayList<>();
        gvGioHang = (GridView) findViewById(R.id.gv_giohang);
        txtTongTien = findViewById(R.id.txtTongTien);
        act_gio_hang_user = findViewById(R.id.act_gio_hang_user);
        btn_dathang = findViewById(R.id.btn_dathang);
        initData();
        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.act_thanh_toan_user, null);

                if(!list.isEmpty()){
                    // Tạo Intent để chuyển từ gio hang sang thanh toan
                    Intent intent = new Intent(ActGioHang.this, ActThanhToan.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(act_gio_hang_user.getContext(), "Giỏ hanng chưa có sản phẩm!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public String formatCurrency(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " VNĐ";
    }

    private void updateTotalAmount() {
        int total = tongTien(list);
        txtTongTien.setText(formatCurrency(total));
    }
    public int tongTien(List<GioHang> list){
        int tien = 0;
        for(int i = 0; i < list.size();i++){
            tien += list.get(i).getGia() * list.get(i).getSoLuong();
        }
        return tien;
    }
    private void initData(){
        MainData mainData = new MainData(this);
        mainData.getWritableDatabase();
        mainData.getReadableDatabase();

        // them gio hang chay
//        mainData.ExecuteSQL("delete from GIOHANG");
//        mainData.ExecuteSQL("INSERT INTO GIOHANG (maSanPham, soLuong, id_user) \n" +
//            "VALUES \n" +
//            "(2, 1,1),\n" +
//            "(3, 1,1),\n" +
//            "(4, 1,1),\n" +
//            "(5, 1,1);");
//        mainData.ExecuteSQL("Delete from SanPham");
//        mainData.ExecuteSQL("INSERT INTO \"SanPham\" (maSanPham, tenSanPham, tendanhmuc, gia, giadagiam, tinhTrang, discount, maPhanHoi, anhSanPham, anhMota1, anhMota2, anhMota3, anhMota4, moTaSanPham) \n" +
//                "VALUES \n" +
//                "(2, 'nguyen quoc viet', 'Món chính', 20000, 20000, 'Còn', 0, NULL, 'content://com.android.providers.media.documents/document/image%3A60', 'content://com.android.providers.media.documents/document/image%3A59', NULL, NULL, NULL, 'khong ngon lam'),\n" +
//                "(3, 'dao quang doanh', 'Khai vị', 200000, 100000, 'Còn', 50, NULL, 'content://com.android.providers.media.documents/document/image%3A80', 'content://com.android.providers.media.documents/document/image%3A59', 'content://com.android.providers.media.documents/document/image%3A60', 'content://com.android.providers.media.documents/document/image%3A81', NULL, 'tam duoc'),\n" +
//                "(4, 'nguyen khac phuoc', 'Món chính', 12000, 10000, 'Còn', 0, NULL, 'content://com.android.providers.media.documents/document/image%3A62', 'content://com.android.providers.media.documents/document/image%3A60', 'content://com.android.providers.media.documents/document/image%3A79', 'content://com.android.providers.media.documents/document/image%3A81', NULL, 'ngon lam'),\n" +
//                "(5, 'pham truong phuong tue', 'Tráng miệng', 100000, 90000, 'Còn', 10, NULL, 'content://com.android.providers.media.documents/document/image%3A60', 'content://com.android.providers.media.documents/document/image%3A64', 'content://com.android.providers.media.documents/document/image%3A82', 'content://com.android.providers.media.documents/document/image%3A63', NULL, 'tam duoc'),\n" +
//                "(6, 'nguyen huu duy', 'Thức uống', 200000, 190000, 'Còn', 5, NULL, 'content://com.android.providers.media.documents/document/image%3A64', 'content://com.android.providers.media.documents/document/image%3A64', 'content://com.android.providers.media.documents/document/image%3A82', 'content://com.android.providers.media.documents/document/image%3A82', NULL, 'tam duoc');\n");

        GioHang giohang = new GioHang(this);
        list = giohang.getAll();
        adapter = new GioHangAdapter(list, R.layout.act_list_gio_hang_user, this);
        gvGioHang.setAdapter(adapter);
        updateTotalAmount();

        mainData.getReadableDatabase();
        mainData.getWritableDatabase();
        // Thiết lập listener để cập nhật tổng tiền khi có thay đổi
        adapter.setOnDataChangedListener(new GioHangAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                updateTotalAmount();
            }
        });

    }

}

