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

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class Profile_User extends AppCompatActivity {
    private MainData db;
    private UserRepository userRepository;
    private TextView history_order,change_password,logout,email;
    private  String getEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MainData db = new MainData(this,"mainData.sqlite",null,1);
        userRepository = new UserRepository(db, this);


        email = findViewById(R.id.email);
        history_order = findViewById(R.id.history_order);
        change_password = findViewById(R.id.change_password);
        logout = findViewById(R.id.logout);

        getEmail = userRepository.getLoggedInUserEmail();
        email.setText(getEmail);

        history_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_User.this,historyOrder.class);
                startActivity(intent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_User.this, Update_password.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.logout();
                Intent intent = new Intent(Profile_User.this, Login.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
