package Dangky_nhap.Views;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.project_nhom8.R;

import Admin.Phuoc.MainActivityAdmin;
import Dangky_nhap.Model.UserRepository;
import Dangky_nhap.Model.Userr;
import Database.MainData.MainData;
import User.Viet.Fragment.GiaoDienDauFragment;

public class Login extends AppCompatActivity{
    private boolean passwordShowing = false;// Biến cờ để theo dõi trạng thái hiển thị mật khẩu
    private EditText emailET,passwordET;
    private ImageView passwordIcon;
    private TextView signUpBtn,forgotPasswordBtn;
    private AppCompatButton signInBtn;
    private MainData db;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        db = new MainData(this,"mainData.sqlite",null,1);

        userRepository = new UserRepository(db,this);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        forgotPasswordBtn =findViewById(R.id.forgotPasswordBtn);
        passwordIcon = findViewById(R.id.passwordIcon);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);

        // Xử lý sự kiện click vào icon mắt (để ẩn/hiện mật khẩu)
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordShowing){// Nếu đang hiện mật khẩu
                    passwordShowing = false;// Đổi cờ trạng thái thành false
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);// Đặt lại kiểu input cho EditText là ẩn mật khẩu
                    passwordIcon.setImageResource(R.drawable.password_showw); // Thay đổi icon thành hiện mật khẩu
                } else { // Nếu đang ẩn mật khẩu
                    passwordShowing = true;
                    passwordET.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// Hiển thị mật khẩu
                    passwordIcon.setImageResource(R.drawable.password_hiden);// Thay đổi icon thành ẩn mật khẩu

                }
                passwordET.setSelection(passwordET.length());// Đảm bảo con trỏ văn bản ở cuối sau khi thay đổi
            }
        });
        if(userRepository.isLoggedIn()){
            String email = userRepository.getLoggedInUserEmail();
            int userId = userRepository.getUserIdByEmail(email);
            navigateToHomePage(userId);
        }

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                Userr user = userRepository.loginUser(email,password);


                if(user !=null){
                    userRepository.saveLoginInfo(email,user.getRole());  // Lưu trạng thái đăng nhập
                    int userId = userRepository.getUserIdByEmail(email);
                    navigateToHomePage(userId);  // Chuyển tới trang chủ
                }
                else{
                    Toast.makeText(Login.this, "Sai email hoặc mật khẩu!", Toast.LENGTH_LONG).show();
                }
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
    // Điều hướng tới trang chủ
    private void navigateToHomePage(int userId) {
        String role = userRepository.getLoggedInUserRole();
        Intent intent;
        if ("Admin".equalsIgnoreCase(role)) {
            intent = new Intent(Login.this, MainActivityAdmin.class);
        } else {
            intent = new Intent(Login.this, Profile_User.class);
        }
        startActivity(intent);
        finish();
    }
}
