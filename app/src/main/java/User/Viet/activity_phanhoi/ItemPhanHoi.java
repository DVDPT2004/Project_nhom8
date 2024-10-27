package User.Viet.activity_phanhoi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_nhom8.R;

import User.Viet.Fragment.FeedbackFragment;
import User.Viet.Fragment.GiaoDienDauFragment;

public class ItemPhanHoi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_listphanhoi);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FeedbackFragment trangchuFragment = new FeedbackFragment();
        fragmentTransaction.replace(R.id.bottomNavigationView, trangchuFragment);
        fragmentTransaction.commit();
    }
}
