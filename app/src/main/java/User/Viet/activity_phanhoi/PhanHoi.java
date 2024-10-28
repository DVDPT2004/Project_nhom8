package User.Viet.activity_phanhoi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_nhom8.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;
import User.Duy.Views.OrderHistoryActivity;
import User.Viet.activity_chitietmonan.ChiTietMonAn;
import User.Viet.activity_trangchu.MenuUser;

public class PhanHoi extends AppCompatActivity {

    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtPhanHoi;
    private Button btnSubmit, btnHuy;
    private ImageView imagemayanh, imagefile;
    private GridView gridView;
    private ArrayList<Bitmap> imageBitmaps = new ArrayList<>();
    private ImageAdapterAnhPhanHoi imageAdapter;
    private int maDonHang;
    private MainData phanHoiDatabase;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;
    private static final int MAX_IMAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phanhoi);

        // Ánh xạ các thành phần từ XML
        edtHoTen = findViewById(R.id.txttenkhachphanhoi);
        edtSoDienThoai = findViewById(R.id.txtsodienthoaiphanhoi);
        edtEmail = findViewById(R.id.txtemailphanhoi);
        edtPhanHoi = findViewById(R.id.txtphanhoi);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnHuy = findViewById(R.id.btnHuy);
        imagemayanh = findViewById(R.id.imagemayanh);
        imagefile = findViewById(R.id.imagefile);
        gridView = findViewById(R.id.gridView);

        // Khởi tạo adapter và GridView
        imageAdapter = new ImageAdapterAnhPhanHoi(imageBitmaps, this);
        gridView.setAdapter(imageAdapter);

        // Khởi tạo cơ sở dữ liệu
        phanHoiDatabase = new MainData(PhanHoi.this, "mainData.sqlite", null, 1);

        // Sự kiện chụp ảnh
        imagemayanh.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(getApplicationContext(), "No camera app found", Toast.LENGTH_SHORT).show();
            }
        });

        // Sự kiện chọn ảnh từ thư viện
        imagefile.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        // Sự kiện gửi phản hồi
        btnSubmit.setOnClickListener(v -> {

            String hoTen = edtHoTen.getText().toString().trim();
            String soDienThoai = edtSoDienThoai.getText().toString().trim();
            String phanHoi = edtPhanHoi.getText().toString().trim();

            if (hoTen.isEmpty() || soDienThoai.isEmpty() || phanHoi.isEmpty()) {
                Toast.makeText(PhanHoi.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                long newRowId = insertFeedback(hoTen, soDienThoai, phanHoi);
                if (newRowId != -1) {
                    Toast.makeText(PhanHoi.this, "Phản hồi của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhanHoi.this, "Có lỗi xảy ra khi gửi phản hồi", Toast.LENGTH_SHORT).show();
                }
                resetForm();
            }
            Intent intent = new Intent(PhanHoi.this, MenuUser.class);
            // Gán ID của mục action_cart
            intent.putExtra("selected_item1", R.id.action_feedback);
            startActivity(intent);
        });

        // Sự kiện nút Hủy
        btnHuy.setOnClickListener(v -> {
            finish();
        });
    }

    // Phương thức để chèn phản hồi vào cơ sở dữ liệu
    // Phương thức để chèn phản hồi vào cơ sở dữ liệu
    private long insertFeedback(String hoTen, String soDienThoai, String phanHoi) {
        SQLiteDatabase db = phanHoiDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKH", hoTen);
        values.put("soDienThoai", soDienThoai);
        values.put("NoiDungKhachPhanHoi", phanHoi);
        MainData mainDataa = new MainData(this);;

        UserRepository userRepository = new UserRepository(mainDataa, this);
        String email = userRepository.getLoggedInUserEmail();
        int user_id = userRepository.getUserIdByEmail(email);


        // Lấy thời gian hiện tại và chuyển đổi định dạng
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = originalFormat.format(new Date()); // Thời gian hiện tại
        //String formattedTime = formatFeedbackTime(currentTime); // Định dạng thời gian

        // Lưu thời gian đã định dạng vào cơ sở dữ liệu
        values.put("thoiGianPhanHoi", currentTime);
        values.put("user_id", user_id);
        maDonHang = getIntent().getIntExtra("maDonHang", -1);
        values.put("maDonHang", maDonHang);
        for (int i = 0; i < imageBitmaps.size(); i++) {
            values.put("media" + (i + 1), saveImageToDatabase(imageBitmaps.get(i)));
        }

        // Chèn vào cơ sở dữ liệu và trả về ID của hàng đã chèn
        long newRowId = db.insert("PhanHoi", null, values);
        db.close();
        return newRowId;
    }

    private String formatFeedbackTime(String thoigianphanhoi) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            // Chuyển đổi từ chuỗi sang Date
            Date date = originalFormat.parse(thoigianphanhoi);

            // Lấy thời gian hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            Calendar feedbackCalendar = Calendar.getInstance();
            feedbackCalendar.setTime(date);

            // Tính số ngày chênh lệch
            long difference = currentCalendar.getTimeInMillis() - feedbackCalendar.getTimeInMillis();
            long daysDifference = difference / (1000 * 60 * 60 * 24); // Chuyển đổi từ milliseconds sang days

            // Kiểm tra xem phản hồi có trong ngày hiện tại không
            if (currentCalendar.get(Calendar.YEAR) == feedbackCalendar.get(Calendar.YEAR) &&
                    currentCalendar.get(Calendar.DAY_OF_YEAR) == feedbackCalendar.get(Calendar.DAY_OF_YEAR)) {
                // Nếu trong ngày hiện tại, hiển thị HH:mm
                return targetFormat.format(date);
            } else if (daysDifference == 1) {
                return "1 ngày trước";
            } else if (daysDifference == 2) {
                return "2 ngày trước";
            } else if (daysDifference >= 3) {
                return dateFormat.format(date); // Trả về định dạng DD/MM/YYYY
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return thoigianphanhoi; // Trả về mặc định nếu không xác định được
    }

    // Lưu ảnh dưới dạng byte[] để lưu vào cơ sở dữ liệu
    private byte[] saveImageToDatabase(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream); // Nén ảnh xuống 10%
        return outputStream.toByteArray();
    }

    // Xử lý kết quả chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null && imageBitmaps.size() < MAX_IMAGES) {
                    imageBitmaps.add(bitmap);
                    imageAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Bạn chỉ có thể chọn tối đa " + MAX_IMAGES + " ảnh", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    if (imageBitmaps.size() + count > MAX_IMAGES) {
                        Toast.makeText(this, "Bạn chỉ có thể chọn tối đa " + MAX_IMAGES + " ảnh", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 0; i < count; i++) {
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        addImageFromUri(uri);
                    }
                } else {
                    Uri uri = data.getData();
                    addImageFromUri(uri);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    private void addImageFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap != null && imageBitmaps.size() < MAX_IMAGES) {
                imageBitmaps.add(bitmap);
            } else {
                Toast.makeText(this, "Bạn chỉ có thể chọn tối đa " + MAX_IMAGES + " ảnh", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Khôi phục form
    private void resetForm() {
        edtHoTen.setText("");
        edtSoDienThoai.setText("");
        edtEmail.setText("");
        edtPhanHoi.setText("");
        imageBitmaps.clear();
        imageAdapter.notifyDataSetChanged();
    }
}
