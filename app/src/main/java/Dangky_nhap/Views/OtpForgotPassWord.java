package Dangky_nhap.Views;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.project_nhom8.R;

public class OtpForgotPassWord extends AppCompatActivity {
    private EditText otpEt1,otpEt2,otpEt3,otpEt4;
    private TextView FgotEmail,resendBtn;
    private AppCompatButton forgotBtn;
    private  boolean  resendEnabled = false;
    private int resendTime = 60;
    private  int selectedETPosition = 0;
    private String  getEmail;
    private String generatedOtp, generateOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_forgot_pass_word);

        otpEt1 = findViewById(R.id.otpEt1);
        otpEt2 = findViewById(R.id.otpEt2);
        otpEt3 = findViewById(R.id.otpEt3);
        otpEt4 = findViewById(R.id.otpEt4);
        forgotBtn = findViewById(R.id.forgotBtn);
        resendBtn = findViewById(R.id.resendBtn);
        FgotEmail = findViewById(R.id.FgotEmail);

        getEmail =  getIntent().getStringExtra("emailFgot");
        FgotEmail.setText(getEmail);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);


        showkeyboard(otpEt1);

        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled){

                    startCountDownTimer();
                }
            }
        });

        // nhan otp tu intent
        generatedOtp = getIntent().getStringExtra("otp");
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();
                if(generateOtp.length() == 4){
                    if(generateOtp.equalsIgnoreCase(generatedOtp)){
                        Toast.makeText(OtpForgotPassWord.this,"Xác thực thành công!",Toast.LENGTH_LONG);
                        Intent intent = new Intent(OtpForgotPassWord.this,ResetPassword.class);
                        intent.putExtra("email",getEmail);
                        startActivity(intent);

                    }else{
                        Toast.makeText(OtpForgotPassWord.this, "Mã OTP không đúng!", Toast.LENGTH_SHORT).show();
                    }
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
