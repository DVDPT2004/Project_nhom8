package Dangky_nhap.Views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_nhom8.R;

import User.Viet.Fragment.FeedbackFragment;
import User.Viet.Fragment.UserFragment;

public class Profile_User extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserFragment trangchuFragment = new UserFragment();
        fragmentTransaction.replace(R.id.bottomNavigationView, trangchuFragment);
        fragmentTransaction.commit();
    }
}

