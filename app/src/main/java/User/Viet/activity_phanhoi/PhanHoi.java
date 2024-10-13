package User.Viet.activity_phanhoi;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.util.ArrayList;

import User.Viet.activity_chitietmonan.ChiTietMonAn;

public class PhanHoi extends AppCompatActivity {

    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtPhanHoi;
    private Button btnSubmit, btnHuy;
    private ImageView imagemayanh, imagefile;
    private GridView gridView;
    private ArrayList<Bitmap> imageBitmaps = new ArrayList<>();
    private ImageAdapterAnhPhanHoi imageAdapter;
    private PhanHoiDatabase phanHoiDatabase;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;

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
        phanHoiDatabase = new PhanHoiDatabase(this);

        // Xử lý sự kiện khi nhấn vào nút chụp ảnh
        imagemayanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

        // Xử lý sự kiện khi nhấn vào nút chọn ảnh từ thư viện
        // Xử lý sự kiện khi nhấn vào nút chọn ảnh hoặc video từ thư viện
        imagefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("*/*"); // Chấp nhận tất cả các loại file
                String[] mimeTypes = {"image/*", "video/*"}; // Chỉ chấp nhận ảnh và video
                galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes); // Đặt MIME type
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Cho phép chọn nhiều file
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });


        // Xử lý sự kiện khi bấm nút Gửi phản hồi
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edtHoTen.getText().toString().trim();
                String soDienThoai = edtSoDienThoai.getText().toString().trim();
                String phanHoi = edtPhanHoi.getText().toString().trim();

                // Kiểm tra các trường bắt buộc
                if (hoTen.isEmpty() || soDienThoai.isEmpty() || phanHoi.isEmpty()) {
                    Toast.makeText(PhanHoi.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Lưu phản hồi vào cơ sở dữ liệu
                    long newRowId = insertFeedback(hoTen, soDienThoai, phanHoi);

                    if (newRowId != -1) {
                        Toast.makeText(PhanHoi.this, "Phản hồi của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PhanHoi.this, "Có lỗi xảy ra khi gửi phản hồi", Toast.LENGTH_SHORT).show();
                    }

                    // Khôi phục thông tin nhập vào
                    resetForm();
                }
            }
        });

        // Xử lý sự kiện khi bấm nút Hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhanHoi.this, ChiTietMonAn.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức để chèn phản hồi vào cơ sở dữ liệu
    private long insertFeedback(String hoTen, String soDienThoai, String phanHoi) {
        SQLiteDatabase db = phanHoiDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKH", hoTen);
        values.put("soDienThoai", soDienThoai);
        values.put("NoiDung", phanHoi);
        values.put("user_id", 1); // Thay đổi giá trị user_id nếu cần
        values.put("maDonHang", 1); // Thay đổi giá trị maDonHang nếu cần

        // Thêm đường dẫn của ảnh vào cơ sở dữ liệu
        for (int i = 0; i < imageBitmaps.size(); i++) {
            values.put("media" + (i + 1), saveImageToDatabase(imageBitmaps.get(i)));
        }

        return db.insert("PhanHoi", null, values);
    }


    // Phương thức để lưu ảnh vào cơ sở dữ liệu
    private byte[] saveImageToDatabase(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    // Xử lý kết quả khi lấy ảnh từ camera hoặc thư viện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    // Kiểm tra xem số lượng ảnh hiện tại có lớn hơn 3 không
                    if (imageBitmaps.size() >= 3) {
                        Toast.makeText(this, "Bạn chỉ có thể chọn tối đa 3 ảnh", Toast.LENGTH_SHORT).show();
                    } else {
                        imageBitmaps.add(bitmap);
                        imageAdapter.notifyDataSetChanged(); // Cập nhật GridView
                    }
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    // Kiểm tra số lượng ảnh đã chọn
                    if (imageBitmaps.size() + count > 3) {
                        Toast.makeText(this, "Bạn chỉ có thể chọn tối đa 3 ảnh", Toast.LENGTH_SHORT).show();
                        return; // Không thêm ảnh nếu vượt quá 4 ảnh
                    }

                    for (int i = 0; i < count; i++) {
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            if (bitmap != null) {
                                imageBitmaps.add(bitmap);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Uri uri = data.getData();
                    if (uri != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            if (bitmap != null) {
                                // Kiểm tra số lượng ảnh đã chọn
                                if (imageBitmaps.size() >= 3) {
                                    Toast.makeText(this, "Bạn chỉ có thể chọn tối đa 3 ảnh", Toast.LENGTH_SHORT).show();
                                } else {
                                    imageBitmaps.add(bitmap);
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                imageAdapter.notifyDataSetChanged(); // Cập nhật GridView
            }
        }
    }


    // Phương thức để khôi phục thông tin nhập vào
    private void resetForm() {
        edtHoTen.setText("");
        edtSoDienThoai.setText("");
        edtEmail.setText("");
        edtPhanHoi.setText("");
        imageBitmaps.clear();
        imageAdapter.notifyDataSetChanged();
    }
}
