package User.Viet.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class LienHeFragment extends Fragment {
    TextView tvName;
    ImageView facebook, call, gmail, igram, youtube, zalo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lienhe, container, false);

        // Initialize views
        tvName = view.findViewById(R.id.tvName);
        facebook = view.findViewById(R.id.facebook);
        call = view.findViewById(R.id.call);
        gmail = view.findViewById(R.id.gmail);
        igram = view.findViewById(R.id.igram);
        youtube = view.findViewById(R.id.youtube);
        zalo = view.findViewById(R.id.zalo);

        MainData mainData = new MainData(getContext());

        // Lấy email của người dùng đã đăng nhập
        UserRepository userRepository = new UserRepository(mainData, getContext());
        String email = userRepository.getLoggedInUserEmail();
        int user_id = userRepository.getUserIdByEmail(email);

        // Truy vấn cơ sở dữ liệu để lấy tên người dùng
        String userName = getUserNameById(user_id, mainData);
        tvName.setText(userName); // Đặt tên người dùng vào TextView

        // Set click listener for Zalo
        zalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                ImageView imageView = new ImageView(requireActivity());
                imageView.setImageResource(R.drawable.zaloqr); // Set image from resources

                builder.setTitle("Zalo")
                        .setView(imageView) // Set the ImageView containing the image in the dialog
                        .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Close the dialog
                            }
                        })
                        .show();
            }
        });

        // Set click listeners for other social media buttons
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String facebookUrl = "https://www.facebook.com/quangg.doanh?mibextid=LQQJ4d";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(facebookUrl));
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtubeUrl = "https://www.youtube.com/@nguyenthaison7950";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(youtubeUrl));
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        igram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String igUrl = "https://www.instagram.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(igUrl));
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gmailUrl = "https://mail.google.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(gmailUrl));
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Liên hệ")
                        .setMessage("Bạn vui lòng liên hệ với số điện thoại: 0372751413")
                        .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Đóng dialog
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    private String getUserNameById(int userId, MainData mainData) {
        String userName = "";
        // Thực hiện truy vấn SQL để lấy tên người dùng
        SQLiteDatabase db = mainData.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT full_name FROM User WHERE user_id = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            userName = cursor.getString(0); // Giả sử cột tên là "name"
            cursor.close();
        }
        return userName;
    }
}
