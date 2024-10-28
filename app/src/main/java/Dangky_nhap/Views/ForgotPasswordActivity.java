package Dangky_nhap.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.OtpGenerator;
import Dangky_nhap.Model.SendEmailTask;
import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailFgot;
    private AppCompatButton forgotBtn;
    private TextView returnFgot;
    private UserRepository userRepository;
    private MainData db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailFgot = findViewById(R.id.emailFgot);
        forgotBtn = findViewById(R.id.forgotBtn);
        returnFgot = findViewById(R.id.returnFgot);

        db = new MainData(this,"mainData.sqlite",null,1);

        userRepository = new UserRepository(db,this);


        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_Fgot = emailFgot.getText().toString().trim();

                // Kiểm tra xem email có hợp lệ không
                if (!email_Fgot.isEmpty()) {
                    // Kiểm tra email có tồn tại trong CSDL không
                    if (userRepository.isEmailExists(email_Fgot)) { // Giả sử bạn đã tạo hàm này trong UserRepository
                        // Tạo OTP
                        String otp = OtpGenerator.generateOtp();
                        // Gửi OTP qua email
                        new SendEmailTask().execute(email_Fgot, otp);
                        // Chuyển đến màn hình OTP với email đã nhập
                        Intent intent = new Intent(ForgotPasswordActivity.this, OtpForgotPassWord.class);
                        intent.putExtra("emailFgot", email_Fgot);
                        intent.putExtra("otp", otp); // Truyền OTP sang màn hình khác
                        startActivity(intent);
                    } else {
                        // Hiển thị thông báo nếu email không tồn tại
                        emailFgot.setError("Email không tồn tại!");
                    }
                } else {
                    // Hiển thị thông báo nếu email chưa được nhập
                    emailFgot.setError("Vui lòng nhập email!");
                }
            }
        });

        returnFgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
