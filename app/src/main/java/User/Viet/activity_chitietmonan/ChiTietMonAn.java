package User.Viet.activity_chitietmonan;


import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import User.Duy.DBGioHangManager;

import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;


import java.text.DecimalFormat;
import java.util.ArrayList;

import Database.MainData.MainData;
import User.Viet.Fragment.CartFragment;
import User.Viet.Modal.Feedback;
import User.Viet.activity_trangchu.MenuUser;

public class ChiTietMonAn extends AppCompatActivity {

    private ImageButton btnExit, cartIcon;
    private TextView mota, description, txtmotamonan, otherImagesTitle,txtTenMonAn,txtGia;
    private GridView grHinhanhkhac;
    private Button btnAddCard,btnAddToCard,btnCancel;
    private LinearLayout layoutMonAn;
    private View viewMo;
    private TextView txtSoLuong;
    private Button btnTang, btnGiam;
    private int soLuong = 1;// Khởi tạo số lượng mặc định là 1
    ImageView imageMonAn;
    ArrayList<Feedback> feedbacksList = new ArrayList<>();
    FeedbackAdapter adapterfeedback;
    ListView lvFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_mon_an);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Ánh xạ các view từ layout
        btnExit = findViewById(R.id.btn_exit);
        cartIcon = findViewById(R.id.cart_icon);
        mota = findViewById(R.id.mota);
        description = findViewById(R.id.description);
        txtmotamonan = findViewById(R.id.txtmotamonan);
        otherImagesTitle = findViewById(R.id.other_images_title);
        grHinhanhkhac = findViewById(R.id.grHinhanhkhac);
        btnAddCard = findViewById(R.id.btnaddcard);
        layoutMonAn = findViewById(R.id.layoutMonAn);
        viewMo = findViewById(R.id.view_mo);
        // Ánh xạ các view
        txtSoLuong = findViewById(R.id.txtSoLuong);
        btnTang = findViewById(R.id.btnTang);
        btnGiam = findViewById(R.id.btnGiam);
        btnAddToCard=findViewById(R.id.btnAddToCart);
        btnCancel=findViewById(R.id.btnCancel);
        imageMonAn = findViewById(R.id.imageMonAn);
        txtTenMonAn = findViewById(R.id.txtTenMonAn);
        txtGia = findViewById(R.id.txtGia);
        cartIcon=findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietMonAn.this, CartFragment.class);
                startActivity(intent);
            }
        });


        // Cập nhật giá trị ban đầu cho txtSoLuong
        txtSoLuong.setText(String.valueOf(soLuong));

        //feedback
        lvFeedback=findViewById(R.id.listViewFeedback);
        adapterfeedback = new FeedbackAdapter(this, feedbacksList);
        lvFeedback.setAdapter(adapterfeedback);
        MainData phanHoiDatabase = new MainData(this,"mainData.sqlite",null,1);
        Cursor cursor = phanHoiDatabase.SelectData("SELECT * FROM PhanHoi WHERE user_id = 2");
        while (cursor.moveToNext()) {
            feedbacksList.add(new Feedback(
                    cursor.getString(9), // Tên người dùng
                    cursor.getString(2), // Ngày phản hồi
                    cursor.getString(3), // Nội dung phản hồi
                    cursor.getBlob(5),   // Hình ảnh 1 (BLOB)
                    cursor.getBlob(6),   // Hình ảnh 2 (BLOB)
                    cursor.getBlob(7)

            ));
        }
        adapterfeedback.notifyDataSetChanged();




        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int maSanPham = intent.getIntExtra("maSanPham", 0);
        String tenMonan = intent.getStringExtra("tenmonan");
        String motaMonAn = intent.getStringExtra("motamonan");
        String hinhanh = intent.getStringExtra("hinhanh");
        String hinhanh1 = intent.getStringExtra("hinhanh1");
        String hinhanh2 = intent.getStringExtra("hinhanh2");
        String hinhanh3 = intent.getStringExtra("hinhanh3");
        String hinhanh4 = intent.getStringExtra("hinhanh4");
        float giaGiam = intent.getFloatExtra("giaGiam", 0);

        DBGioHangManager dbGioHangManager = new DBGioHangManager(ChiTietMonAn.this);

// Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        Cursor cursorCheck = dbGioHangManager.selectData("SELECT * FROM giohang WHERE id_sp = " + maSanPham);
        if (cursorCheck != null && cursorCheck.getCount() > 0) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật giao diện nút
            btnAddCard.setBackgroundColor(ContextCompat.getColor(ChiTietMonAn.this, R.color.darker_gray));
            btnAddCard.setText("Đã thêm vào giỏ hàng");
            btnAddCard.setEnabled(false); // Vô hiệu hóa nút
        }
        if (cursorCheck != null) {
            cursorCheck.close(); // Đóng Cursor sau khi kiểm tra
        }

// Định dạng số
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        decimalFormat.setDecimalSeparatorAlwaysShown(false);

// Hiển thị thông tin món ăn
        txtmotamonan.setText(motaMonAn);
        txtGia.setText(decimalFormat.format(giaGiam) + " VNĐ");
        txtTenMonAn.setText(tenMonan);

// Thiết lập hình ảnh chính
        if (hinhanh != null && !hinhanh.isEmpty()) {
            imageMonAn.setImageURI(Uri.parse(hinhanh)); // Thiết lập hình ảnh chính
        } else {
            imageMonAn.setImageResource(R.drawable.doan1); // Hình ảnh mặc định
        }

// Tạo mảng hình ảnh cho GridView
        String[] images = new String[] { hinhanh1, hinhanh2, hinhanh3, hinhanh4 };

// Thiết lập ImageAdapter cho GridView
        ImageAdapter adapter = new ImageAdapter(this, images);
        grHinhanhkhac.setAdapter(adapter);



        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietMonAn.this, MenuUser.class);
                startActivity(intent);

            }
        });
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Làm mờ giao diện hiện tại
                viewMo.setVisibility(View.VISIBLE);
                // Hiện giao diện món ăn mới
                layoutMonAn.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý cho nút thoát (nếu cần)
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity
            }
        });
        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong++; // Tăng số lượng
                txtSoLuong.setText(String.valueOf(soLuong)); // Cập nhật lại TextView
            }
        });

        // Sự kiện nhấn nút giảm
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong > 1) { // Đảm bảo số lượng không giảm xuống dưới 1
                    soLuong--; // Giảm số lượng
                    txtSoLuong.setText(String.valueOf(soLuong)); // Cập nhật lại TextView
                }
            }
        });

        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo AlertDialog để xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietMonAn.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc chắn muốn thêm món ăn này vào giỏ hàng không?");

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu xác nhận, thay đổi màu và chữ của nút
                        layoutMonAn.setVisibility(View.GONE); // Ẩn giao diện món ăn
                        viewMo.setVisibility(View.GONE); // Ẩn lớp làm mờ
                        btnAddCard.setBackgroundColor(ContextCompat.getColor(ChiTietMonAn.this, R.color.darker_gray));
                        btnAddCard.setText("Đã thêm vào giỏ hàng"); // Cập nhật chữ
                        DBGioHangManager dbGioHangManager = new DBGioHangManager(ChiTietMonAn.this);
                        String sql = "INSERT INTO giohang (id_sp, soluong) VALUES (" + maSanPham + ", " + soLuong + ")";
                        try{
                            dbGioHangManager.execSQL(sql);
                        }catch(SQLiteConstraintException e){

                            Toast.makeText(ChiTietMonAn.this, "Sản phẩm đã tồn tại trong giỏ hàng.", Toast.LENGTH_SHORT).show(); // Hiển thị thông báo lên màn hình
                        }
                        btnAddCard.setEnabled(false); // Vô hiệu hóa nút nếu không muốn nhấn lại
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng dialog nếu nhấn Hủy
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show(); // Hiển thị AlertDialog
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMonAn.setVisibility(View.GONE); // Ẩn giao diện món ăn
                viewMo.setVisibility(View.GONE); // Ẩn lớp làm mờ
            }
        });


    }
}