package User.Viet.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project_nhom8.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;
import User.Viet.Modal.Feedback;
import User.Viet.Modal.ItemFeedback;
import User.Viet.activity_phanhoi.ItemFeedbackAdapter;

public class FeedbackFragment extends Fragment {
    private ListView listViewContacts;
    private ItemFeedbackAdapter adapter;
    private List<ItemFeedback> feedbackList;
    private List<Feedback> feedbackImages; // Danh sách để lưu trữ các đối tượng Feedback
    private MainData database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_layout_listphanhoi, container, false);

        // Khởi tạo ListView và Adapter
        listViewContacts = view.findViewById(R.id.listViewContacts);
        feedbackList = new ArrayList<>();
        feedbackImages = new ArrayList<>(); // Khởi tạo danh sách ảnh phản hồi

        // Khởi tạo cơ sở dữ liệu
        database = new MainData(getContext());

        // Lấy dữ liệu từ cơ sở dữ liệu
        loadFeedbackData();

        // Khởi tạo adapter
        adapter = new ItemFeedbackAdapter(getContext(), feedbackList);
        listViewContacts.setAdapter(adapter);

        // Thiết lập sự kiện khi ấn vào từng item trong ListView
        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy phản hồi được chọn
                ItemFeedback selectedFeedback = feedbackList.get(position);
                Feedback selectedFeedbackImages = feedbackImages.get(position); // Lấy ảnh phản hồi tương ứng

                // Hiển thị dialog với chi tiết phản hồi
                showFeedbackDetails(getContext(), selectedFeedback, selectedFeedbackImages);
            }
        });

        return view;
    }

    // Hàm tải dữ liệu từ bảng PhanHoi
    private void loadFeedbackData() {
        // Khởi tạo cơ sở dữ liệu và truy vấn
        database.getReadableDatabase();
        database.getWritableDatabase();
        UserRepository userRepository = new UserRepository(database, getContext());

        // Lấy email người dùng đã đăng nhập và `user_id`
        String email = userRepository.getLoggedInUserEmail();
        int user_id = userRepository.getUserIdByEmail(email);

        // Truy vấn kết hợp với bảng DonHang để lấy mã đơn hàng và thời gian đặt hàng
        Cursor cursor = database.SelectData("SELECT PhanHoi.*, DonHang.maDonHang, DonHang.ngayGioDatHang " +
                "FROM PhanHoi " +
                "JOIN DonHang ON PhanHoi.maDonHang = DonHang.maDonHang " + // Sử dụng maDonHang để JOIN
                "WHERE PhanHoi.user_id = " + user_id + " " +
                "ORDER BY PhanHoi.thoiGianPhanHoi DESC");


        if (cursor != null) {
            while (cursor.moveToNext()) {
                String tenKH = cursor.getString(9); // Cột tên khách hàng từ PhanHoi
                String thoiGianPhanHoi = cursor.getString(2);
                String noiDungKhachPhanHoi = cursor.getString(3);
                String noiDungAdminPhanHoi = cursor.getString(4);


                // Lấy mã đơn hàng và thời gian đặt hàng
                int maDonHang = cursor.getInt(10); // Cột maDonHang từ DonHang
                String ngayGioDatHang = cursor.getString(13);
                Log.d("LOGG",maDonHang+" "+ngayGioDatHang);// Cột ngayGioDatHang từ DonHang

                // Thêm phản hồi vào danh sách
                feedbackList.add(new ItemFeedback(tenKH, thoiGianPhanHoi, noiDungKhachPhanHoi, maDonHang, ngayGioDatHang));

                // Lấy hình ảnh từ cơ sở dữ liệu dưới dạng byte[]
                byte[] image1 = cursor.getBlob(5); // Thay đổi chỉ số tương ứng với cột ảnh 1
                byte[] image2 = cursor.getBlob(6); // Thay đổi chỉ số tương ứng với cột ảnh 2
                byte[] image3 = cursor.getBlob(7); // Thay đổi chỉ số tương ứng với cột ảnh 3

                feedbackImages.add(new Feedback(noiDungAdminPhanHoi, tenKH, thoiGianPhanHoi, noiDungKhachPhanHoi, image1, image2, image3));
            }
            cursor.close(); // Đóng con trỏ sau khi sử dụng
        } else {
            Toast.makeText(getContext(), "Không có phản hồi nào", Toast.LENGTH_SHORT).show();
        }
    }


    // Hàm hiển thị chi tiết phản hồi trong một Dialog
    private void showFeedbackDetails(Context context, ItemFeedback feedback, Feedback feedbackImages) {
        // Tạo một LinearLayout để chứa các thành phần
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16); // Padding cho layout

        // Tạo TextView để hiển thị chi tiết phản hồi
        TextView textViewDetails = new TextView(context);
        textViewDetails.setText(
                "Mã đơn hàng:"+feedback.getMaDonHang()+"\n"+
                        "Thời gian đặt hàng:"+feedback.getNgayGioDatHang()+"\n"+
                        "Người gửi: " + feedback.getUserName() + "\n" +
                        "Thời gian phản hồi: " + feedback.getFeedbackTime() + "\n" +
                        "Nội dung phản hồi: " + feedback.getFeedbackContent() + "\n" +
                        "Ảnh phản hồi: ");

        // Tạo một LinearLayout để chứa các ImageView xếp ngang
        LinearLayout imageLayout = new LinearLayout(context);
        imageLayout.setOrientation(LinearLayout.HORIZONTAL); // Xếp ngang
        imageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Tạo ImageView cho từng ảnh
        ImageView imageView1 = new ImageView(context);
        ImageView imageView2 = new ImageView(context);
        ImageView imageView3 = new ImageView(context);

        // Chuyển đổi byte[] thành Bitmap và đặt vào ImageView
        if (feedbackImages.getImage1() != null) {
            Bitmap bitmap = byteArrayToBitmap(feedbackImages.getImage1());
            imageView1.setImageBitmap(bitmap);
        } else {
            imageView1.setImageDrawable(null);
        }

        if (feedbackImages.getImage2() != null) {
            Bitmap bitmap1 = byteArrayToBitmap(feedbackImages.getImage2());
            imageView2.setImageBitmap(bitmap1);
        } else {
            imageView2.setImageDrawable(null);
        }

        if (feedbackImages.getImage3() != null) {
            Bitmap bitmap2 = byteArrayToBitmap(feedbackImages.getImage3());
            imageView3.setImageBitmap(bitmap2);
        } else {
            imageView3.setImageDrawable(null);
        }

        // Thiết lập kích thước cho các ImageView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, // Chiều rộng tự động điều chỉnh
                200); // Đặt chiều cao cho các ảnh
        params.weight = 1; // Tạo các ImageView có cùng kích thước

        imageView1.setLayoutParams(params);
        imageView2.setLayoutParams(params);
        imageView3.setLayoutParams(params);

        // Thêm các ImageView vào imageLayout
        imageLayout.addView(imageView1);
        imageLayout.addView(imageView2);
        imageLayout.addView(imageView3);

        // Thêm TextView và imageLayout vào layout chính
        layout.addView(textViewDetails);
        layout.addView(imageLayout);

        TextView textViewAdminResponse = new TextView(context);
        textViewAdminResponse.setText("\n\n\n"+"Phản hồi từ admin: " + feedbackImages.getNoiDungAdminPhanHoi());
        layout.addView(textViewAdminResponse); // Thêm phản hồi từ admin vào layout
        // Tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chi tiết phản hồi")
                .setView(layout) // Đặt layout chứa các thành phần vào dialog
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    // Phương thức chuyển đổi byte[] thành Bitmap
    private Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
