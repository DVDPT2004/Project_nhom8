package Dangky_nhap.Views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class Update_password extends AppCompatActivity {
    private EditText old_password,new_password,confirm_password;
    private AppCompatButton change_password_button;
    private MainData db;
    private UserRepository userRepository;
    private  String getEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        change_password_button = findViewById(R.id.change_password_button);
        db = new MainData(this,"mainData.sqlite",null,1);
        userRepository = new UserRepository(db,this);

        getEmail = userRepository.getLoggedInUserEmail();

        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_pass();
                Intent intent = new Intent(Update_password.this, Profile_User.class);
            }
        });

    }
    private  void update_pass(){
        String oldPassword = old_password.getText().toString().trim();
        String newPassword = new_password.getText().toString().trim();
        String confirmPassword = confirm_password.getText().toString().trim();

        // Kiểm tra thông tin đầu vào
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu cũ
        if (userRepository.loginUser(getEmail, oldPassword) == null) {
            Toast.makeText(this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu mới
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật mật khẩu
        if (userRepository.updatePassword(getEmail, newPassword)) {
            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại trang trước đó
        } else {
            Toast.makeText(this, "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }
}
