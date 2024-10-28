
package User.Duy;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;
import User.Duy.ActThanhToan;
import User.Duy.GioHang;
import User.Duy.GioHangAdapter;

public class ActGioHang extends Fragment {
    GridView gvGioHang;
    Button btn_dathang;
    List<GioHang> list;
    GioHangAdapter adapter;
    TextView txtTongTien;
    LinearLayout act_gio_hang_user;
    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.act_gio_hang_user, container, false);

        // Initialize UI components
        gvGioHang = view.findViewById(R.id.gv_giohang);
        txtTongTien = view.findViewById(R.id.txtTongTien);
        act_gio_hang_user = view.findViewById(R.id.act_gio_hang_user);
        btn_dathang = view.findViewById(R.id.btn_dathang);

        list = new ArrayList<>();
        initData();

        // Set button click listener
        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.act_thanh_toan_user, null);

                if (!list.isEmpty()) {
                    Intent intent = new Intent(getActivity(), ActThanhToan.class);
                    Log.e("tongtien", String.valueOf(tongTien(list)));
                    startActivity(intent);
                } else {
                    Toast.makeText(requireContext(), "Giỏ hàng chưa có sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }
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

    public int tongTien(List<GioHang> list) {
        int tien = 0;
        for (int i = 0; i < list.size(); i++) {
            tien += list.get(i).getGia() * list.get(i).getSoLuong();
        }
        return tien;
    }

    private void initData() {
        MainData mainData = new MainData(requireContext());
        mainData.getWritableDatabase();
        mainData.getReadableDatabase();

        // Initialize cart data
        GioHang giohang = new GioHang(requireContext());
        list = giohang.getAll();
        adapter = new GioHangAdapter(list, R.layout.act_list_gio_hang_user, requireContext());
        gvGioHang.setAdapter(adapter);
        updateTotalAmount();

        // Set listener to update total amount when data changes
        adapter.setOnDataChangedListener(new GioHangAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                updateTotalAmount();
            }
        });
    }
}

