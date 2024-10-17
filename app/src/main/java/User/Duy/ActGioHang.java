package User.Duy;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class ActGioHang extends AppCompatActivity {
    GridView gvGioHang;
    Button btn_dathang;
    List<GioHang> list;
    GioHangAdapter adapter;
    TextView txtTongTien;
    LinearLayout act_gio_hang_user;
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
                // Tạo Intent để chuyển từ gio hang sang thanh toan
                Intent intent = new Intent(ActGioHang.this, ActThanhToan.class);
                startActivity(intent);
            }
        });
//        adapter = new GioHangAdapter(list,  R.layout.act_list_gio_hang_user,this);
//        adapter.setOnDataChangedListener(new GioHangAdapter.OnDataChangedListener() {
//            @Override
//            public void onDataChanged() {
//                updateTotalAmount();
//            }
//        });
    }


    private void updateTotalAmount() {
        Log.d("ActGioHang", "updateTotalAmount called");
        long total = tongTien(list);
        txtTongTien.setText(String.valueOf(total) + " VND");
        Log.d("ActGioHang", "Total amount: " + total);
    }
    public long tongTien(List<GioHang> list){
        long tien = 0;
        for(int i = 0; i < list.size();i++){
            tien += list.get(i).getGia() * list.get(i).getSoLuong();
        }
        return tien;
    }
     private void initData(){

//        for(int i = 0; i < 10; i++){
//            list.add(new GioHang(1, "SP" + i, 123, 2, R.drawable.ff));
//        }
//        adapter = new GioHangAdapter(list,  R.layout.act_list_gio_hang_user,this);
//        gvGioHang.setAdapter(adapter);
//        txtTongTien.setText(String.valueOf(tongTien(list)));
//         updateTotalAmount();
    }

}

