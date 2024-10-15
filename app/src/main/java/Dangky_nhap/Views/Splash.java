package Dangky_nhap.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Thời gian hiển thị splash (3000ms = 3 giây)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển sang màn hình chính (RegisterActivity hoặc màn hình chính của bạn)
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish(); // Đóng SplashActivity để không quay lại được
            }
        }, 3000);
    }
}
