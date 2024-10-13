package User.Viet.activity_chitietmonan;


import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import User.Viet.activity_phanhoi.PhanHoiDatabase;
import User.Viet.activity_trangchu.Trangchu;

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

        // Cập nhật giá trị ban đầu cho txtSoLuong
        txtSoLuong.setText(String.valueOf(soLuong));

        //feedback
        lvFeedback=findViewById(R.id.listViewFeedback);
        adapterfeedback = new FeedbackAdapter(this, feedbacksList);
        lvFeedback.setAdapter(adapterfeedback);
        PhanHoiDatabase phanHoiDatabase = new PhanHoiDatabase(ChiTietMonAn.this);
        Cursor cursor = phanHoiDatabase.rawQuery("SELECT * FROM PHANHOI");
        while (cursor.moveToNext()) {
            feedbacksList.add(new Feedback(
                    cursor.getString(8), // Tên người dùng
                    cursor.getString(2), // Ngày phản hồi
                    cursor.getString(3), // Nội dung phản hồi
                    cursor.getBlob(4),   // Hình ảnh 1 (BLOB)
                    cursor.getBlob(5),   // Hình ảnh 2 (BLOB)
                    cursor.getBlob(6)

            ));
        }
        adapterfeedback.notifyDataSetChanged();




        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String tenMonan = intent.getStringExtra("tenmonan");
        String motaMonAn = intent.getStringExtra("motamonan");
        int hinhanh = intent.getIntExtra("hinhanh", 0);
        int hinhanh1 = intent.getIntExtra("hinhanh1", 0);
        int hinhanh2 = intent.getIntExtra("hinhanh2", 0);
        int hinhanh3 = intent.getIntExtra("hinhanh3", 0);
        int hinhanh4 = intent.getIntExtra("hinhanh4", 0);
        float giaGiam = intent.getFloatExtra("giaGiam", 0);

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        // Hiển thị thông tin món ăn
        txtmotamonan.setText(motaMonAn);
        txtGia.setText(formatter.format(giaGiam) + " VNĐ");
        txtTenMonAn.setText(tenMonan);
        imageMonAn.setImageResource(hinhanh);


        // Tạo mảng chứa các hình ảnh
        int[] images = {hinhanh1, hinhanh2, hinhanh3, hinhanh4};

        // Thiết lập adapter cho GridView
        ImageAdapter adapter = new ImageAdapter(this, images);
        grHinhanhkhac.setAdapter(adapter);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietMonAn.this, Trangchu.class);
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
                        btnAddCard.setBackgroundColor(ContextCompat.getColor(ChiTietMonAn.this, R.color.black));
                        btnAddCard.setText("Đã thêm vào giỏ hàng"); // Cập nhật chữ
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