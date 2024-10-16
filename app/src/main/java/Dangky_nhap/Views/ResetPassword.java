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

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class ResetPassword extends AppCompatActivity {
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
    private EditText password,conPassword;
    private ImageView passwordIcon,conPasswordIcon;
    private AppCompatButton signUpBtn,newPasswordBtn;
    private TextView signInBtn;
    private MainData db;
    private UserRepository userRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        password = findViewById(R.id.passwordET);
        conPassword = findViewById(R.id.conPasswordET);
        passwordIcon = findViewById(R.id.passwordIcon);
        conPasswordIcon = findViewById(R.id.conPasswordIcon);
        newPasswordBtn = findViewById(R.id.newPasswordBtn);
        signInBtn = findViewById(R.id.signInBtn);
        newPasswordBtn = findViewById(R.id.newPasswordBtn);
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
        db = new MainData(this);
        userRepository = new UserRepository(db,this);
        String email = getIntent().getStringExtra("email");
        newPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = password.getText().toString();
                String newConPassword = conPassword.getText().toString();
                // Kiểm tra xem hai mật khẩu có khớp nhau không
                if (newPassword.equals(newConPassword)) {
                    boolean isUpdated = userRepository.updatePassword(email, newPassword);// Truyền email vào đây
                    if (isUpdated) {
                        Toast.makeText(ResetPassword.this, "Mật khẩu đã được cập nhật!", Toast.LENGTH_SHORT).show();
                        // Điều hướng về trang đăng nhập sau khi cập nhật thành công
                        Intent intent = new Intent(ResetPassword.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ResetPassword.this, "Cập nhật mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetPassword.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
