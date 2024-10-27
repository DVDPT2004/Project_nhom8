package User.Viet.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Database.MainData.MainData;
import User.Duy.DBGioHangManager;
import User.Duy.GioHang;
import User.Duy.GioHangAdapter;
import User.Duy.ActThanhToan;

public class CartFragment extends Fragment {
    private GridView gvGioHang;
    private Button btn_dathang;
    private List<GioHang> list;
    private GioHangAdapter adapter;
    private TextView txtTongTien;
    private LinearLayout act_gio_hang_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_gio_hang_user, container, false);

        // Ánh xạ các thành phần giao diện
        gvGioHang = view.findViewById(R.id.gv_giohang);
        txtTongTien = view.findViewById(R.id.txtTongTien);
        act_gio_hang_user = view.findViewById(R.id.act_gio_hang_user);
        btn_dathang = view.findViewById(R.id.btn_dathang);

        // Thiết lập padding cho các cạnh
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.act_gio_hang_user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo dữ liệu
        list = new ArrayList<>();
        initData();

        // Thiết lập sự kiện nhấn nút đặt hàng
        btn_dathang.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ActThanhToan.class);
            startActivity(intent);
        });

        return view;
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
        for (GioHang item : list) {
            tien += item.getGia() * item.getSoLuong();
        }
        return tien;
    }

    private void initData() {
        DBGioHangManager dbgiohang = new DBGioHangManager(getActivity());

        list = new GioHang(getActivity()).getAll();
        adapter = new GioHangAdapter(list, R.layout.act_list_gio_hang_user, getActivity());
        gvGioHang.setAdapter(adapter);
        updateTotalAmount();

        // Thiết lập listener để cập nhật tổng tiền khi có thay đổi
        adapter.setOnDataChangedListener(this::updateTotalAmount);
    }
}
