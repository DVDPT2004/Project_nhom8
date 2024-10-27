package Dangky_nhap.Views;

import static Dangky_nhap.Model.OtpGenerator.generateOtp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.SendEmailTask;
import Dangky_nhap.Model.UserRepository;
import Dangky_nhap.Model.Userr;
import Database.MainData.MainData;

public class Register extends AppCompatActivity {
    private   String role = "User";
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    private EditText full_name,email,password,conPassword;
    private ImageView passwordIcon,conPasswordIcon;
    private AppCompatButton signUpBtn;
    private TextView signInBtn,signUp;
    private RadioGroup roleGroup;
    private MainData db;
    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        full_name = findViewById(R.id.fullNameET);
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        conPassword = findViewById(R.id.conPasswordET);
        passwordIcon = findViewById(R.id.passwordIcon);
        conPasswordIcon = findViewById(R.id.conPasswordIcon);
        signUp = findViewById(R.id.signUp);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);

        //roleGroup = findViewById(R.id.roleGroup);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordShowing){
                    passwordShowing = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_showw);
                }else{
                    passwordShowing = true;
                    password.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hiden);
                }
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conPasswordShowing){
                    conPasswordShowing = false;
                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_showw);
                }else{
                    conPasswordShowing = true;
                    conPassword.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_hiden);
                }
                conPassword.setSelection(conPassword.length());
            }
        });
        db = new MainData(this,"mainData.sqlite",null,1);

        userRepository = new UserRepository(db,this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = full_name.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String conPass = conPassword.getText().toString();
                // String role = ((RadioButton) findViewById(roleGroup.getCheckedRadioButtonId())).getText().toString();

                // Kiểm tra các trường dữ liệu
                if (name.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng nhập tên đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mail.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    Toast.makeText(Register.this, "Vui lòng nhập email hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (conPass.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(conPass)) {
                    Toast.makeText(Register.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Userr newUser = new Userr(name,mail,pass,role);

                if(userRepository.registerUser(newUser)){
                    // Tạo mã OTP ngẫu nhiên
                    String otp = generateOtp();
                    // Gửi mã OTP đến email
                    new SendEmailTask().execute(mail, otp);
                    Intent intent = new Intent(Register.this, OTPVerification.class);
                    intent.putExtra("email", mail); // Gửi email cho OTPVerification
                    intent.putExtra("otp", otp); // Gửi mã OTP
                    startActivity(intent);

                } else {
                    Toast.makeText(Register.this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));

            }
        });
    }
    private void showSignUpDialog() {
        // Tạo dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_check);

        // Lấy các nút từ dialog
        AppCompatButton confirmButton = dialog.findViewById(R.id.btnConfirm);
        AppCompatButton cancelButton = dialog.findViewById(R.id.btnCancel);
        EditText passwordEditText = dialog.findViewById(R.id.pass_check); // Lấy EditText nhập mật khẩu
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(700, 500); // Đặt chiều rộng là 800 pixels và chiều cao là 600 pixels

        // Xử lý sự kiện cho nút "Xác nhận"
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword = passwordEditText.getText().toString(); // Lấy mật khẩu người dùng nhập vào
                // Kiểm tra mật khẩu (Giả sử mật khẩu admin là "admin123" - thay đổi theo yêu cầu thực tế)
                if (enteredPassword.equals("admin123")) {
                    role = "Admin";
                    Toast.makeText(Register.this, "Đăng ký thành công vs role là admin!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        // Xử lý sự kiện cho nút "Thoát"
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Đóng dialog
            }
        });
        // Hiển thị dialog
        dialog.show();
    }
}