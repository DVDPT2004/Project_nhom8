package Dangky_nhap.Views;

import static Dangky_nhap.Model.OtpGenerator.generateOtp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.SendEmailTask;
import Dangky_nhap.Model.UserRepository;
import Dangky_nhap.Model.Userr;
import Database.MainData.MainData;

public class OTPVerification extends AppCompatActivity {
    private EditText otpEt1,otpEt2,otpEt3,otpEt4;
    private TextView resendBtn,otpEmail;
    private  boolean  resendEnabled = false;
    private int resendTime = 60;
    private String generatedOtp,generateOtp; // Mã OTP được gửi đến email
    private Button verifyBtn;
    private  int selectedETPosition = 0;
    private  String getfullName, getemail,getPass,getRole;
    private UserRepository userRepository;
    private MainData db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        otpEt1 = findViewById(R.id.otpEt1);
        otpEt2 = findViewById(R.id.otpEt2);
        otpEt3 = findViewById(R.id.otpEt3);
        otpEt4 = findViewById(R.id.otpEt4);

        resendBtn = findViewById(R.id.resendBtn);
        verifyBtn = findViewById(R.id.verifyBtn);
        otpEmail = findViewById(R.id.otpEmail);

        db = new MainData(this,"mainData.sqlite",null,1);
        userRepository = new UserRepository(db,this);

        getfullName = getIntent().getStringExtra("fullName");
        getemail = getIntent().getStringExtra("email");
        getPass = getIntent().getStringExtra("password");
        getRole = getIntent().getStringExtra("role");
        generatedOtp = getIntent().getStringExtra("otp");

        otpEmail.setText(getemail);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);


        showkeyboard(otpEt1);

        startCountDownTimer();



        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();
                if(generateOtp.length() == 4){
                    // So sánh mã OTP nhập vào với mã OTP đã gửi
                    if (generateOtp.equals(generatedOtp)) {
                        Userr newUser = new Userr(getfullName,getemail,getPass,getRole);
                        if(userRepository.addUser(newUser)){
                            Toast.makeText(OTPVerification.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();
                        }
                        // Chuyển đến màn hình đăng nhập hoặc màn hình chính
                        startActivity(new Intent(OTPVerification.this, Login.class));
                        finish();
                    } else {
                        Toast.makeText(OTPVerification.this, "Mã OTP không đúng!", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled){
                    // Tạo mã OTP mới
                    String newOtp = generateOtp();

                    // Gửi mã OTP đến email
                    new SendEmailTask().execute(getemail, newOtp);

                    // Cập nhật mã OTP đã gửi
                    generatedOtp = newOtp;

                    // Hiển thị thông báo gửi mã thành công
                    Toast.makeText(OTPVerification.this, "Mã OTP đã được gửi lại đến email của bạn.", Toast.LENGTH_SHORT).show();

                    startCountDownTimer();
                }
            }
        });


    }
    private  void showkeyboard(EditText otpET) {
        otpET.requestFocus();
        InputMethodManager inputMethodManager =  (InputMethodManager)  getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET,InputMethodManager.SHOW_IMPLICIT);
    }
    private  void startCountDownTimer(){
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));
        new CountDownTimer(resendTime * 1000,1000) {

            @Override
            public void onTick(long millisUntilFinished ) {
                resendBtn.setText("Resend Code (" + (millisUntilFinished / 1000) + ")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));


            }
        }.start();
    }
    private  final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                if (selectedETPosition < 3) { // Chỉ cho phép chuyển khi chưa ở ô cuối
                    selectedETPosition++;
                    switch (selectedETPosition) {
                        case 1:
                            showkeyboard(otpEt2);
                            break;
                        case 2:
                            showkeyboard(otpEt3);
                            break;
                        case 3:
                            showkeyboard(otpEt4);
                            break;
                    }
                }
            } else {
                // Nếu ô hiện tại bị xóa, quay lại ô trước đó
                if (selectedETPosition > 0) {
                    selectedETPosition--;
                    switch (selectedETPosition) {
                        case 0:
                            showkeyboard(otpEt1);
                            break;
                        case 1:
                            showkeyboard(otpEt2);
                            break;
                        case 2:
                            showkeyboard(otpEt3);
                            break;
                    }
                }
            }
        }
    };

    public  boolean  onKeyUp(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_DEL){
            if(selectedETPosition == 3){
                selectedETPosition = 2;
                showkeyboard(otpEt3);
            }
            else if(selectedETPosition == 2){
                selectedETPosition = 1;
                showkeyboard(otpEt2);
            }else if(selectedETPosition == 1){
                selectedETPosition = 0;
                showkeyboard(otpEt1);

            }
            return  true;
        }
        else{
            return super.onKeyUp(keyCode,event);
        }

    }
}
