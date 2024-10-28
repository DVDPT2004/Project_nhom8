package User.Duy.Views;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;
import User.Duy.Adapter.DonHangAdapter;
import User.Duy.Modal.DonHang;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DonHangAdapter adapter;
    private List<DonHang> donHangList;
    private MainData db;
    private ImageButton btn_exit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyc_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách và adapter
        donHangList = fetchOrderHistory();
        adapter = new DonHangAdapter(donHangList, this);
        recyclerView.setAdapter(adapter);

        btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(view -> {
            finish();
        });
    }
    private List<DonHang> fetchOrderHistory() {
        List<DonHang> orders = new ArrayList<>();
        db = new MainData(this,"mainData.sqlite",null,1);
        UserRepository userRepository = new UserRepository(db, this);
        String email = userRepository.getLoggedInUserEmail();
        int user_id = userRepository.getUserIdByEmail(email);

        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM DonHang where user_id = " + user_id, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("ngayGioDatHang"));
                int thanhTien = cursor.getInt(cursor.getColumnIndexOrThrow("ThanhTien"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("noiGiaoHang"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai"));
                String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("phuongThucThanhToan"));
                String customerName = cursor.getString(cursor.getColumnIndexOrThrow("tenKH"));
                int orderStatus = cursor.getInt(cursor.getColumnIndexOrThrow("tinhTrangDonHang"));

                // Convert date string to Date object if necessary
                Date orderDate = new Date(); // Replace with actual date conversion if needed

                DonHang order = new DonHang(id, orderDate, thanhTien, userId, address, phone, paymentMethod, customerName, orderStatus);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

}
