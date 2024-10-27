package Dangky_nhap.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_nhom8.R;

import Admin.Doanh.DoanhThu;
import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class Profile_admin extends AppCompatActivity {
    private TextView logout,email;
    private UserRepository userRepository;
    private MainData db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new MainData(this, "mainData.sqlite", null, 1);
        userRepository = new UserRepository(db, this); // Khởi tạo UserRepository
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        logout = findViewById(R.id.logout);
        email = findViewById(R.id.email);
        String Email= userRepository.getLoggedInUserEmail();
        email.setText(Email);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.logout();
                Intent intent = new Intent(Profile_admin.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
